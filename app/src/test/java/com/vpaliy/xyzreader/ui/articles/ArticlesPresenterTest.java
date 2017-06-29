package com.vpaliy.xyzreader.ui.articles;

import org.mockito.junit.MockitoJUnitRunner;
import com.vpaliy.xyzreader.FakeProvider;
import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;
import com.vpaliy.xyzreader.ui.articles.ArticlesContract.View;
import rx.Observable;
import rx.schedulers.Schedulers;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(MockitoJUnitRunner.class)
public class ArticlesPresenterTest {

    @Mock
    private View view;

    @Mock
    private IRepository<Article> repository;

    @Mock
    private BaseSchedulerProvider schedulerProvider;

    @InjectMocks
    ArticlesPresenter presenter;

    @Before
    public void setUp(){
        presenter.attachView(view);
        when(schedulerProvider.io()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.ui()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void showsDataToViewOnStartMethod(){
        when(repository.get()).thenReturn(Observable.just(FakeProvider.provideArticleList()));
        presenter.start();

        verify(view).setLoadingIndicator(eq(true));
        verify(view).setLoadingIndicator(eq(false));
        verify(view).showList(anyList());
    }

    @Test
    public void showsEmptyMessageOnStartMethod(){
        when(repository.get()).thenReturn(Observable.just(null));
        presenter.start();

        verify(view).setLoadingIndicator(eq(true));
        verify(view).setLoadingIndicator(eq(false));
        verify(view).showEmptyMessage();
    }

    @Test
    public void showsErrorMessageOnStartMethod(){
        when(repository.get()).thenReturn(Observable.error(new Exception()));
        presenter.start();

        verify(view).setLoadingIndicator(eq(true));
        verify(view).setLoadingIndicator(eq(false));
        verify(view).showErrorMessage();
    }

}
