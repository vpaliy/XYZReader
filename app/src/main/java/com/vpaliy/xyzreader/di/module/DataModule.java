package com.vpaliy.xyzreader.di.module;

import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.data.ArticleRepository;
import com.vpaliy.xyzreader.data.cache.CacheStore;
import com.vpaliy.xyzreader.data.mapper.ArticleMapper;
import com.vpaliy.xyzreader.data.mapper.Mapper;
import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.data.scheduler.SchedulerProvider;
import com.vpaliy.xyzreader.data.source.DataSource;
import com.vpaliy.xyzreader.data.source.local.LocalSource;
import com.vpaliy.xyzreader.data.source.qualifier.Local;
import com.vpaliy.xyzreader.data.source.qualifier.Remote;
import com.vpaliy.xyzreader.data.source.remote.RemoteSource;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton @Provides
    Mapper<Article,ArticleEntity> articleMapper(){
        return new ArticleMapper();
    }

    @Singleton @Provides
    IRepository<Article> articleRepository(ArticleRepository repository){
        return repository;
    }

    @Singleton @Provides
    BaseSchedulerProvider schedulerProvider(){
        return new SchedulerProvider();
    }

    @Singleton @Provides
    CacheStore<Article> cacheStore(){
        return new CacheStore<>(100);
    }

    @Singleton @Provides @Remote
    DataSource<ArticleEntity> remoteDataSource(RemoteSource source){
        return source;
    }

    @Singleton @Provides @Local
    DataSource<ArticleEntity> localDataSource(LocalSource source){
        return source;
    }

}
