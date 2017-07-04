package com.vpaliy.xyzreader.ui;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.article.ArticleActivity;
import com.vpaliy.xyzreader.ui.article.Dummy;
import com.vpaliy.xyzreader.ui.base.Constants;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;

import java.util.List;
import java.util.Map;

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
            String transitionName=activity.getString(R.string.poster_transition)+event.articleId;
            String imageShot=transitionName;
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
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imagePair,
                    Pair.create(event.image,activity.getString(R.string.transition_background)));
            activity.setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    super.onMapSharedElements(names, sharedElements);
                    if (sharedElements.size() != names.size()) {
                        // couldn't map all shared elements
                        final View sharedShot = sharedElements.get(imageShot);
                        if (sharedShot != null) {
                            // has shot so add shot background, mapped to same view
                            sharedElements.put(activity.getString(R.string.transition_background), sharedShot);
                        }
                    }
                }
            });
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }

}
