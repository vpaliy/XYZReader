package com.vpaliy.xyzreader.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.vpaliy.xyzreader.data.cache.CacheStore;
import com.vpaliy.xyzreader.data.mapper.Mapper;
import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.data.source.DataSource;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;
import java.util.List;
import rx.Completable;
import rx.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.vpaliy.xyzreader.data.source.qualifier.Local;
import com.vpaliy.xyzreader.data.source.qualifier.Remote;

@Singleton
public class ArticleRepository implements IRepository<Article>{

    private Mapper<Article,ArticleEntity> mapper;
    private CacheStore<Article> cacheStore;
    private BaseSchedulerProvider schedulerProvider;
    private DataSource<ArticleEntity> localSource;
    private DataSource<ArticleEntity> remoteSource;
    private Context context;

    @Inject
    public ArticleRepository(@Local DataSource<ArticleEntity> localSource,
                             @Remote DataSource<ArticleEntity> remoteSource,
                             BaseSchedulerProvider schedulerProvider,
                             CacheStore<Article> cacheStore,
                             Mapper<Article,ArticleEntity> mapper,
                             Context context){
        this.mapper=mapper;
        this.localSource=localSource;
        this.remoteSource=remoteSource;
        this.cacheStore=cacheStore;
        this.schedulerProvider=schedulerProvider;
        this.context=context;
    }

    @Override
    public Observable<Article> get(int id) {
        if(!cacheStore.isInCache(id)) {
            if (isNetworkConnection()) {
                return remoteSource.get(id)
                        .doOnNext(this::saveToLocal)
                        .map(mapper::map)
                        .doOnNext(this::saveInCache);
            }
            return localSource.get(id)
                    .map(mapper::map)
                    .doOnNext(this::saveInCache);
        }
        return Observable.just(cacheStore.get(id));
    }

    private void saveToLocal(ArticleEntity entity){
        if(entity!=null) {
            Completable.fromCallable(() -> {
                localSource.insert(entity);
                return true;
            }).subscribeOn(schedulerProvider.multi()).subscribe();
        }
    }

    private void saveToLocal(List<ArticleEntity> entities){
        if(entities!=null){
            Completable.fromCallable(()->{
                entities.forEach(localSource::insert);
                return true;
            }).subscribeOn(schedulerProvider.multi()).subscribe();
        }
    }

    private void saveInCache(Article article){
        if(article!=null){
            cacheStore.put(article.getId(),article);
        }
    }

    private void saveInCache(List<Article> articles){
        if(articles!=null){
            articles.forEach(article -> cacheStore.put(article.getId(),article));
        }
    }

    @Override
    public Observable<List<Article>> get() {
        if(isNetworkConnection()) {
            return remoteSource.get()
                    .doOnNext(this::saveToLocal)
                    .map(mapper::map)
                    .doOnNext(this::saveInCache);
        }
        return localSource.get()
                .map(mapper::map)
                .doOnNext(this::saveInCache);
    }

    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}
