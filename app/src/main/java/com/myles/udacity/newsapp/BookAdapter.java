package com.myles.udacity.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by asus on 5/12/2016.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = this.getItem(position);

        ((TextView) listItemView.findViewById(R.id.text_title)).setText(currentBook.getTitle());
        ((TextView) listItemView.findViewById(R.id.text_section)).setText(currentBook.getPublisher());

        return listItemView;
    }
}
