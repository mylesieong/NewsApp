package com.myles.udacity.newsapp;

import java.util.Date;

/**
 * Created by asus on 5/12/2016.
 */

public class News {
    private String mTitle;
    private String mPublisher;
    private String mURL;

    public News() {
        super();
    }

    /**
     * Setters
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setPublisher(String publisher) {
        this.mPublisher = publisher;
    }

    public void setURL(String url) {
        this.mURL = url;
    }

    /**
     * Getters
     */
    public String getTitle() {
        return this.mTitle;
    }

    public String getPublisher() {
        return this.mPublisher;
    }

    public String getURL() {
        return this.mURL;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().toString());
        sb.append(this.mTitle);
        sb.append("/");
        sb.append(this.mPublisher);
        sb.append("/");
        sb.append(this.mURL);
        sb.append("#");
        return sb.toString();
    }
}
