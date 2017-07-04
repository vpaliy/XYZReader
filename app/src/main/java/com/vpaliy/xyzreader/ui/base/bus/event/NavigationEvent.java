package com.vpaliy.xyzreader.ui.base.bus.event;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationEvent {

    public final int articleId;
    public final ImageView image;
    public final View background;
    public final TextView date;
    public final TextView author;
    public final TextView title;
    public final View parent;

    private NavigationEvent(ImageView image, View background,
                            TextView date, TextView author, TextView title,
                            View parent, int article){
        this.articleId=article;
        this.background=background;
        this.image=image;
        this.date=date;
        this.author=author;
        this.title=title;
        this.parent=parent;
    }

    public static NavigationEvent navigate(ImageView image, View background,
                                           TextView date, TextView author, TextView title,
                                           View parent, int articleId){
        return new NavigationEvent(image,background,date,author,title,parent,articleId);
    }
}
