package com.myles.udacity.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    /**
     * 地震 loader ID 的常量值。我们可选择任意整数。
     * 仅当使用多个 loader 时该设置才起作用。
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String BOOK_API_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Empty view for listview
        ((ListView) this.findViewById(R.id.list)).setEmptyView((TextView) findViewById(R.id.empty_view));

        LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        //Setup button onclick listener to activate the task
        /**
        ((Button) findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 引用 LoaderManager，以便与 loader 进行交互。
                LoaderManager loaderManager = getLoaderManager();

                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            }
        });
         */
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        //String searchQuery = ((TextView) findViewById(R.id.text_input)).getText().toString();
        String searchQuery = "android";
        URL searchUrl = null;
        try {
            searchUrl = new URL(BOOK_API_REQUEST_URL + searchQuery);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 为给定 URL 创建新 loader
        return new BookAsyncTaskLoader(this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        if (books == null) {
            return;
        }
        BookAdapter adapter = new BookAdapter(MainActivity.this, books);
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // empty the list in main activity
    }
}
