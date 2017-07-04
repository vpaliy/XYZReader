package com.vpaliy.xyzreader.data.source.remote;

import com.vpaliy.xyzreader.data.ArticleEntity;
import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface ArticleService {
    @GET("/TNTest/xyzreader/master/data.json")
    Observable<List<ArticleEntity>> provideArticles();
}
