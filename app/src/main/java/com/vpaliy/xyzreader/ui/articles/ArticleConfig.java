package com.vpaliy.xyzreader.ui.articles;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArticleConfig implements IArticlesConfig {

    private SharedPreferences sh;

    @Inject
    public ArticleConfig(Context context){

    }

    @Override
    public ViewConfig fetchConfig() {
        return null;
    }

    @Override
    public void save(ViewConfig config) {

    }
}
