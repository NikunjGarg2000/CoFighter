package com.wordpress.myselfnikunj.cofighter.Model;

import android.content.Context;

public class MediaModel {
    private Context context;
    private String title, description, url, imageLink, publishedAt;

    public MediaModel() {
    }

    public MediaModel(Context context, String title, String description, String url, String imageLink, String publishedAt) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageLink = imageLink;
        this.publishedAt = publishedAt;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
