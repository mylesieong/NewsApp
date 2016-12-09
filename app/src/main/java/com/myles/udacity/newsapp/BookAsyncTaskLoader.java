package com.myles.udacity.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

/**
 * Created by asus on 9/12/2016.
 */

public class BookAsyncTaskLoader extends AsyncTaskLoader<List<Book>> {

    private URL mURL;

    public BookAsyncTaskLoader(Context context, URL url){
        super(context);
        this.mURL = url;
    }

    @Override
    public List<Book> loadInBackground() {
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return null;
        }

        URL url = mURL;
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        List<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }

    @Override
    protected void onStartLoading() {
        this.forceLoad();
    }

    /**
     * Support method
     * @param inputStream
     * @return
     * @throws IOException
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Support method
     * @param json
     * @return
     */
    private List<Book> extractFeatureFromJson(String json) {
        ArrayList<Book> books = new ArrayList<Book>();
        try {
            JSONObject baseJsonResponse = new JSONObject(json);
            JSONArray featureArray = baseJsonResponse.getJSONArray("items");

            // If there are results in the features array
            for (int i = 0; i < featureArray.length(); i++) {
                JSONObject firstFeature = featureArray.getJSONObject(i);
                JSONObject properties = firstFeature.getJSONObject("volumeInfo");

                String title = properties.getString("title");
                String publisher = properties.getString("publisher");
                JSONArray authorList = properties.getJSONArray("authors");
                ArrayList<String> authors = new ArrayList<String>();
                for (int j = 0; j < authorList.length(); j++) {
                    authors.add(authorList.getString(j));
                }
                String date = properties.getString("publishedDate");

                Book book = new Book();
                book.setTitle(title);
                book.setPublisher(publisher);
                book.setPublishDate(date);
                book.setAuthors(authors.toArray(new String[]{}));
                books.add(book);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }
}
