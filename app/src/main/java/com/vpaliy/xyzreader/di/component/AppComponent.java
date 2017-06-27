package com.vpaliy.xyzreader.di.component;

import android.content.Context;

import com.vpaliy.xyzreader.di.module.ApplicationModule;
import com.vpaliy.xyzreader.di.module.DataModule;
import com.vpaliy.xyzreader.di.module.NetworkModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,
        DataModule.class, NetworkModule.class})
public interface AppComponent {
    IRepository<Article> repository();
    Context context();

}
