package com.vpaliy.xyzreader.ui.base.bus.event;


public class NavigationEvent {

    public final int articleId;

    private NavigationEvent(int article){
        this.articleId=article;
    }

    public static NavigationEvent navigate(int articleId){
        return new NavigationEvent(articleId);
    }
}
