package com.vpaliy.xyzreader.data;

import com.google.gson.annotations.SerializedName;

public class ArticleEntity {

    @SerializedName("id")
    private int id;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("thumb")
    private String posterUrl;

    @SerializedName("photo")
    private String backdropUrl;

    @SerializedName("published_date")
    private String publishedDate;

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBody() {
        return body;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
