package com.myles.udacity.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String BOOK_API_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Empty view for listview
        ((ListView) this.findViewById(R.id.list)).setEmptyView((TextView) findViewById(R.id.empty_view));

        //Setup button onclick listener to activate the task
        ((Button) findViewById(R.id.button_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, MainActivity.this);
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.v("MylesDebug", "onCreateLoader");
        String searchQuery = ((TextView) findViewById(R.id.text_input)).getText().toString();
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
        Log.v("MylesDebug", "onloadFinihs");
        if (books == null) {
            return;
        }
        BookAdapter adapter = new BookAdapter(MainActivity.this, books);
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.v("MylesDebug", "onLoaderRet");
        // empty the list in main activity
    }
}
