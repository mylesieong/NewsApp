package com.myles.udacity.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 5/12/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> newses) {
        super(context, 0, newses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNews = this.getItem(position);

        ((TextView) listItemView.findViewById(R.id.text_title)).setText(currentNews.getTitle());
        ((TextView) listItemView.findViewById(R.id.text_section)).setText(currentNews.getPublisher());
        ((TextView) listItemView.findViewById(R.id.text_url)).setText(currentNews.getURL());

        return listItemView;
    }
}
