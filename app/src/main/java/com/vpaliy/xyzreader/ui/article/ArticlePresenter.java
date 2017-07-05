package com.vpaliy.xyzreader.ui.article;

import com.vpaliy.xyzreader.data.scheduler.BaseSchedulerProvider;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.domain.IRepository;
import com.vpaliy.xyzreader.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import static com.vpaliy.xyzreader.ui.article.ArticleContract.View;
import static dagger.internal.Preconditions.checkNotNull;

@ViewScope
public class ArticlePresenter implements ArticleContract.Presenter{

    private IRepository<Article> repository;
    private BaseSchedulerProvider schedulerProvider;
    private View view;

    @Inject
    public ArticlePresenter(IRepository<Article> repository,
                            BaseSchedulerProvider schedulerProvider){
        this.repository=repository;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void loadArticle(int id) {
        repository.get(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processArticle,this::catchError);
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();
    }

    private void processArticle(Article article){
        if(article!=null){
            view.showArticle(article);
        }else{
            view.showEmptyMessage();
        }
    }
}
