package com.vpaliy.xyzreader.ui.article;

import android.support.annotation.NonNull;

import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.BasePresenter;
import com.vpaliy.xyzreader.ui.base.BaseView;

public interface ArticleContract {

    interface Presenter extends BasePresenter<View> {
        void loadArticle(int id);
        void attachView(@NonNull View view);
    }

    interface View extends BaseView<Presenter>{
        void showArticle(Article article);
        void showErrorMessage();
        void showEmptyMessage();
        void attachPresenter(@NonNull Presenter presenter);
    }
}
