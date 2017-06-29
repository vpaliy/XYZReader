package com.vpaliy.xyzreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;

import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.article.ArticleActivity;
import com.vpaliy.xyzreader.ui.base.Constants;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public Navigator(){}

    public void navigateToDetails(Activity activity, NavigationEvent event){
        Intent intent=new Intent(activity,ArticleActivity.class);
        intent.putExtra(Constants.EXTRA_ARTICLE_ID,event.articleId);
        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
            String transitionName=Integer.toString(event.articleId);
            event.image.setTransitionName(transitionName);
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,event.image,transitionName);
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }
}
