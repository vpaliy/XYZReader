package com.vpaliy.xyzreader.data.source.remote;

import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.data.source.DataSource;

import java.util.List;
import rx.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteSource extends DataSource<ArticleEntity> {

    private ArticleService service;

    @Inject
    public RemoteSource(ArticleService service){
        this.service=service;
    }

    @Override
    public Observable<List<ArticleEntity>> get() {
       return service.provideArticles();
    }

    @Override
    public Observable<ArticleEntity> get(int id) {
        return null;
    }
}
