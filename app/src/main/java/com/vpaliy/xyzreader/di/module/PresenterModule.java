package com.vpaliy.xyzreader.di.module;

import com.vpaliy.xyzreader.di.scope.ViewScrope;
import com.vpaliy.xyzreader.ui.article.ArticleContract;
import com.vpaliy.xyzreader.ui.article.ArticlePresenter;
import com.vpaliy.xyzreader.ui.articles.ArticlesContract;
import com.vpaliy.xyzreader.ui.articles.ArticlesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @ViewScrope @Provides
    ArticlesContract.Presenter articlesPresenter(ArticlesPresenter presenter){
        return presenter;
    }

    @ViewScrope @Provides
    ArticleContract.Presenter articlePresenter(ArticlePresenter presenter){
        return presenter;
    }
}
