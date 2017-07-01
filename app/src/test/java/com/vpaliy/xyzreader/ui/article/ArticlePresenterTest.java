package com.vpaliy.xyzreader.ui.article;

import com.vpaliy.xyzreader.FakeProvider;
import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.vpaliy.xyzreader.ui.article.ArticleContract.View;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticlePresenterTest {

    @Mock
    private View view;

    @Mock
    private IRepository<Article> repository;

    @Mock
    private BaseSchedulerProvider schedulerProvider;

    @InjectMocks
    ArticlePresenter presenter;

    @Before
    public void setUp(){
        presenter.attachView(view);
        when(schedulerProvider.io()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.ui()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void showsDataToViewOnStartMethod(){
        when(repository.get(1)).thenReturn(Observable.just(FakeProvider.provideArticle()));
        presenter.loadArticle(1);

        verify(view).showArticle(any(Article.class));
    }

    @Test
    public void showsEmptyMessageOnStartMethod(){
        when(repository.get(1)).thenReturn(Observable.just(null));
        presenter.loadArticle(1);

        verify(view).showEmptyMessage();
    }

    @Test
    public void showsErrorMessageOnStartMethod(){
        when(repository.get(1)).thenReturn(Observable.error(new Exception()));
        presenter.loadArticle(1);

        verify(view).showErrorMessage();
    }
}
