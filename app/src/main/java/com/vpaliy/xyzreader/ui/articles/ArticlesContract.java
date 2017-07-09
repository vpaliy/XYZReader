package com.vpaliy.xyzreader.ui.articles;

import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.BasePresenter;
import com.vpaliy.xyzreader.ui.base.BaseView;
import java.util.List;
import android.support.annotation.NonNull;
import static com.vpaliy.xyzreader.ui.articles.IArticlesConfig.ViewConfig;

public interface ArticlesContract {

    interface Presenter extends BasePresenter<View> {
        void start();
        void stop();
        void refresh();
        void attachView(@NonNull View view);
    }

    interface View extends BaseView<Presenter>{
        void showList(List<Article> articles, ViewConfig config);
        void changeConfig(ViewConfig config);
        void showErrorMessage();
        void showEmptyMessage();
        void setLoadingIndicator(boolean isLoading);
        void attachPresenter(@NonNull Presenter presenter);
    }
}
