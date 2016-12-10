package com.myles.udacity.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final int NEWS_LOADER_ID = 1;

    private static final String NEWS_API_REQUEST_URL =
            "http://content.guardianapis.com/search?q=news&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ListView) this.findViewById(R.id.list)).setEmptyView((TextView) findViewById(R.id.empty_view));

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);

        ((ListView) this.findViewById(R.id.list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView)view.findViewById(R.id.text_url)).getText().toString()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.v("MylesDebug", "onCreateLodaer");
        URL searchUrl = null;
        try {
            searchUrl = new URL(NEWS_API_REQUEST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 为给定 URL 创建新 loader
        return new NewsAsyncTaskLoader(this, searchUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        Log.v("MylesDebug", "onLoadFinish");
        if (newses == null) {
            return;
        }
        NewsAdapter adapter = new NewsAdapter(MainActivity.this, newses);
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.v("MylesDebug", "onReseLoader");
        // empty the list in main activity
    }
}
