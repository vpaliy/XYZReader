package com.vpaliy.xyzreader.ui.articles;

import javax.inject.Singleton;

@Singleton
public interface IArticlesConfig {

    void save(ViewConfig config);
    void subscribe(Callback callback);
    void unsubscribe(Callback callback);
    ViewConfig fetchConfig();

    enum ViewConfig{
        GRID,
        LIST
    }

    interface Callback {
        void onConfigChanged(ViewConfig config);
    }
}
