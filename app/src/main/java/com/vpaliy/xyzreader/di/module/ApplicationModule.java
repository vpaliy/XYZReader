package com.vpaliy.xyzreader.di.module;

import android.content.Context;

import com.vpaliy.xyzreader.ui.Navigator;

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
}
