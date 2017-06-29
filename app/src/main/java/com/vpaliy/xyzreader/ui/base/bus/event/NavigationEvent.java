package com.vpaliy.xyzreader.ui.base.bus.event;


import android.widget.ImageView;

public class NavigationEvent {

    public final int articleId;
    public final ImageView image;

    private NavigationEvent(ImageView image, int article){
        this.articleId=article;
        this.image=image;
    }

    public static NavigationEvent navigate(ImageView image,  int articleId){
        return new NavigationEvent(image,articleId);
    }
}
