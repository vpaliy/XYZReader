package com.vpaliy.xyzreader.domain;

public class Article {

    private int id;
    private String backdropUrl;
    private String posterUrl;
    private String title;
    private String body;
    private String author;
    private String publishedDate;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public String getBody() {
        return body;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getPublishedDate() {
        return publishedDate;
    }


}
