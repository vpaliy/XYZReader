package com.vpaliy.xyzreader.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vpaliy.xyzreader.FakeProvider;
import com.vpaliy.xyzreader.data.cache.CacheStore;
import com.vpaliy.xyzreader.data.mapper.Mapper;
import com.vpaliy.xyzreader.data.scheduler.ImmediateScheduler;
import com.vpaliy.xyzreader.data.source.DataSource;
import com.vpaliy.xyzreader.domain.Article;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import rx.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleRepositoryTest {

    private static final ImmediateScheduler SCHEDULER =new ImmediateScheduler();

    @Mock
    private CacheStore<Article> cacheStore;

    @Mock
    private DataSource<ArticleEntity> local;

    @Mock
    private DataSource<ArticleEntity> remote;

    @Mock
    private Mapper<Article,ArticleEntity> mapper;

    @Mock
    private ConnectivityManager manager;

    @Mock
    private Context context;

    private ArticleRepository repository;

    @Before
    public void setUp(){
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
        when(mapper.map(anyList())).thenReturn(FakeProvider.provideArticleList());

        repository=new ArticleRepository(local,remote, SCHEDULER,cacheStore,mapper,context);
    }

    @Test
    public void getsAllArticlesIfThereIsNetwork(){
        setNetworkAvailable();
        setDataSourceResult(remote);

        repository.get()
                .observeOn(SCHEDULER.io())
                .subscribeOn(SCHEDULER.ui())
                .subscribe(this::resultShouldNotBeNull);

        verify(remote).get();
        verify(mapper).map(anyList());
        verify(manager).getActiveNetworkInfo();
        verify(context).getSystemService(Context.CONNECTIVITY_SERVICE);
        verify(cacheStore,times(FakeProvider.LIST_SIZE)).put(anyInt(),any(Article.class));
        verify(local,times(FakeProvider.LIST_SIZE)).insert(any(ArticleEntity.class));
    }

    @Test
    public void getsAllArticlesWhenThereIsNoNetwork(){
        setDataSourceResult(local);

        repository.get()
                .observeOn(SCHEDULER.io())
                .subscribeOn(SCHEDULER.ui())
                .subscribe(this::resultShouldNotBeNull);

        verify(local).get();
        verify(mapper).map(anyList());
        verify(manager).getActiveNetworkInfo();
        verify(cacheStore,times(FakeProvider.LIST_SIZE)).put(anyInt(),any(Article.class));
        verify(context).getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private void resultShouldNotBeNull(Object result){
        assertThat(result, notNullValue());
    }

    private void setDataSourceResult(DataSource<ArticleEntity> source){
        when(source.get()).thenReturn(Observable.just(FakeProvider.provideArticleEntityList()));
    }

    private void setNetworkAvailable(){
        NetworkInfo info= Mockito.mock(NetworkInfo.class);
        when(manager.getActiveNetworkInfo()).thenReturn(info);
        when(info.isConnectedOrConnecting()).thenReturn(true);
    }
}
