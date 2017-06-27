package com.vpaliy.xyzreader.data;

public class ArticleEntity {

    private int id;
    private String author;
    private String title;
    private String body;
    private String posterUrl;
    private String backdropUrl;
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
