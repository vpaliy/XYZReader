package com.vpaliy.xyzreader.ui.articles;

import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.di.scope.ViewScope;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;
import java.util.List;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import rx.subscriptions.CompositeSubscription;
import static com.vpaliy.xyzreader.ui.articles.ArticlesContract.View;
import static dagger.internal.Preconditions.checkNotNull;

@ViewScope
public class ArticlesPresenter implements ArticlesContract.Presenter {

    private View view;
    private IRepository<Article> repository;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeSubscription subscription;

    @Inject
    public ArticlesPresenter(@NonNull IRepository<Article> repository,
                             @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository=checkNotNull(repository);
        this.schedulerProvider=checkNotNull(schedulerProvider);
        this.subscription=new CompositeSubscription();
    }

    @Override
    public void start() {
        fetchData();
    }

    @Override
    public void stop() {
        subscription.clear();
    }

    @Override
    public void refresh() {
        subscription.clear();
        fetchData();
    }

    private void fetchData(){
        view.setLoadingIndicator(true);
        subscription.add(repository.get()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::process,this::catchError,
                        ()->view.setLoadingIndicator(false)));
    }
    private void process(List<Article> articleList){
        if(articleList==null||articleList.size()==0){
            view.showEmptyMessage();
        }else{
            view.showList(articleList);
        }
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();
        view.setLoadingIndicator(false);
    }

    @Override
    public void attachView(@NonNull View view) {
        checkNotNull(view);
        this.view=view;
    }
}
