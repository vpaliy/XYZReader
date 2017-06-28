package com.vpaliy.xyzreader.ui.articles;

import android.support.annotation.NonNull;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.BaseFragment;

import java.util.List;

import static com.vpaliy.xyzreader.ui.articles.ArticlesContract.Presenter;
import static dagger.internal.Preconditions.checkNotNull;

public class ArticlesFragment extends BaseFragment
        implements ArticlesContract.View {

    private Presenter presenter;

    @Override
    public void showList(List<Article> articles) {

    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {

    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        checkNotNull(presenter);
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void injectDependencies() {

    }
}
