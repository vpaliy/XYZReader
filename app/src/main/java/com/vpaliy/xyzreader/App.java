package com.vpaliy.xyzreader;


import android.app.Application;

import com.vpaliy.xyzreader.di.component.AppComponent;
import com.vpaliy.xyzreader.di.component.DaggerAppComponent;
import com.vpaliy.xyzreader.di.module.ApplicationModule;
import com.vpaliy.xyzreader.di.module.DataModule;
import com.vpaliy.xyzreader.di.module.NetworkModule;

public class App extends Application {

    private AppComponent component;
    private static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE=this;
        buildComponent();
    }

    private void buildComponent(){
        component= DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .networkModule(new NetworkModule())
                .build();
    }

    public static App app(){
        return INSTANCE;
    }

    public AppComponent component(){
        return component;
    }
}
