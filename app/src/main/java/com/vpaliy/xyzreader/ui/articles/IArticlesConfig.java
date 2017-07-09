package com.vpaliy.xyzreader.ui.articles;

import javax.inject.Singleton;

@Singleton
public interface IArticlesConfig {

    void save(ViewConfig config);
    ViewConfig fetchConfig();

    enum ViewConfig{
        GRID,
        LIST
    }
}
