package com.vpaliy.xyzreader.di.component;

import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.ui.article.ArticleFragment;
import com.vpaliy.xyzreader.ui.articles.ArticlesFragment;

import dagger.Component;

@Component(modules = PresenterModule.class,
        dependencies = AppComponent.class)
public interface ViewComponent {
    void inject(ArticlesFragment fragment);
    void inject(ArticleFragment fragment);
}
