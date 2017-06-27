package com.vpaliy.xyzreader.ui.articles;

import android.support.annotation.NonNull;

import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.BasePresenter;
import com.vpaliy.xyzreader.ui.base.BaseView;

import java.util.List;

public interface ArticlesContract {

    interface Presenter extends BasePresenter<View> {
        void start();
        void stop();
        void refresh();
        void attachView(@NonNull View view);
    }

    interface View extends BaseView<Presenter>{
        void showList(List<Article> articles);
        void showErrorMessage();
        void showEmptyMessage();
        void setLoadingIndicator(boolean isLoading);
        void attachPresenter(@NonNull Presenter presenter);
    }
}
