package com.vpaliy.xyzreader.domain;

import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;

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

    public String getFormattedDate(){
        Time time = new Time();
        time.parse3339(getPublishedDate());
        return DateUtils.getRelativeTimeSpanString(time.toMillis(false),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL).toString();
    }

    public List<String> getSplitBody(){
        body= Html.fromHtml(body).toString();
        TextUtils.SimpleStringSplitter splitter=new TextUtils.SimpleStringSplitter('.');
        splitter.setString(body);
        List<String> list=new ArrayList<>();
        while(splitter.hasNext()) {
            StringBuilder builder = new StringBuilder();
            for(int index=0;index<10;index++){
                builder.append(splitter.next());
                builder.append('.');
                if(!splitter.hasNext()) break;
            }
            list.add(builder.toString());
        }

        return list;
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
