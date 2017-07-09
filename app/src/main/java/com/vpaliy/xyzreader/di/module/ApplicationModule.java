package com.vpaliy.xyzreader.di.module;

import android.content.Context;

import com.vpaliy.xyzreader.ui.Navigator;
import com.vpaliy.xyzreader.ui.articles.ArticleConfig;
import com.vpaliy.xyzreader.ui.articles.IArticlesConfig;
import com.vpaliy.xyzreader.ui.base.bus.RxBus;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context=context;
    }

    @Singleton @Provides
    Context provideContext(){
        return context;
    }

    @Singleton @Provides
    Navigator provideNavigator(){
        return new Navigator();
    }

    @Singleton @Provides
    RxBus provideBus(){
        return new RxBus();
    }

    @Singleton @Provides
    IArticlesConfig provideArticleConfig(ArticleConfig articleConfig){
        return articleConfig;
    }
}
