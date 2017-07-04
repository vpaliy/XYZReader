package com.vpaliy.xyzreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;

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
            String transitionName=activity.getString(R.string.poster_transition);
            String image=activity.getString(R.string.poster_transition);
            String title=activity.getString(R.string.title_transition);
            String background=activity.getString(R.string.background_transition);
            String date=activity.getString(R.string.date_transition);
            String author=activity.getString(R.string.author_transition);
            String parent=activity.getString(R.string.transition_background);
            event.image.setTransitionName(transitionName);
            event.title.setTransitionName(title);
            event.background.setTransitionName(background);
            event.date.setTransitionName(date);
            event.author.setTransitionName(author);
            event.parent.setTransitionName(parent);
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                    //shared elements
                    Pair.create(event.image,image),Pair.create(event.parent,parent),
                    Pair.create(event.background,background), Pair.create(event.title,title),
                    Pair.create(event.date,date),Pair.create(event.author,author));
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }

}
