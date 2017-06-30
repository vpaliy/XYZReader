package com.vpaliy.xyzreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

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
            String transitionName=activity.getString(R.string.image_transition)+event.articleId;
            event.image.setTransitionName(transitionName);
            Pair<View,String> imagePair=new Pair<>(event.image,transitionName);
            transitionName=activity.getString(R.string.background_transition)+event.articleId;
            Pair<View,String> backgroundPair=new Pair<>(event.background,transitionName);
            transitionName=activity.getString(R.string.date_transition)+event.articleId;
            Pair<View,String> datePair=new Pair<>(event.date,transitionName);
            transitionName=activity.getString(R.string.title_transition)+event.articleId;
            Pair<View,String> titlePair=new Pair<>(event.title,transitionName);
            transitionName=activity.getString(R.string.author_transition)+event.articleId;
            Pair<View,String> authorPair=new Pair<>(event.author,transitionName);
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imagePair,datePair,
                    titlePair,authorPair,backgroundPair);
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }

}
