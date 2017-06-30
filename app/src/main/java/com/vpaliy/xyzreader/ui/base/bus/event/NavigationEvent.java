package com.vpaliy.xyzreader.ui.base.bus.event;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationEvent {

    public final int articleId;
    public final ImageView image;
    public final View background;
    public final View date;
    public final View author;
    public final View title;

    private NavigationEvent(ImageView image, View background,
                            View date, View author, View title,
                            int article){
        this.articleId=article;
        this.background=background;
        this.image=image;
        this.date=date;
        this.author=author;
        this.title=title;
    }

    public static NavigationEvent navigate(ImageView image, View background,
                                           View date, View author, View title,
                                           int articleId){
        return new NavigationEvent(image,background,date,author,title,articleId);
    }
}
