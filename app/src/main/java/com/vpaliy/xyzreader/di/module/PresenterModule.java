package com.vpaliy.xyzreader.di.module;

import com.vpaliy.xyzreader.di.scope.ViewScope;
import com.vpaliy.xyzreader.ui.article.ArticleContract;
import com.vpaliy.xyzreader.ui.article.ArticlePresenter;
import com.vpaliy.xyzreader.ui.articles.ArticlesContract;
import com.vpaliy.xyzreader.ui.articles.ArticlesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ViewScope
    @Provides
    ArticlesContract.Presenter articlesPresenter(ArticlesPresenter presenter){
        return presenter;
    }

    @ViewScope
    @Provides
    ArticleContract.Presenter articlePresenter(ArticlePresenter presenter){
        return presenter;
    }
}
