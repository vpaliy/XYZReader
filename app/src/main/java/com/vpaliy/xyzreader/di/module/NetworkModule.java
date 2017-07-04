package com.vpaliy.xyzreader.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vpaliy.xyzreader.data.source.remote.ArticleService;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL="https://raw.githubusercontent.com/";

    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;


    @Provides
    @Singleton
    Interceptor provideOkHttpInterceptor(){
        return (chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                    .build();

            Request newRequest = originalRequest.newBuilder()
                    .url(newHttpUrl)
                    .build();

            return chain.proceed(newRequest);});
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@NonNull Context context, @NonNull Interceptor interceptor) {
        Builder builder = new Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE));
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ArticleService provideArticleService(Retrofit retrofit){
        return retrofit.create(ArticleService.class);
    }
}
