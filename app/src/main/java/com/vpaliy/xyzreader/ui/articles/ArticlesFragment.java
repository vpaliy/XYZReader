package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.di.component.DaggerViewComponent;
import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.BaseFragment;
import com.vpaliy.xyzreader.ui.base.bus.RxBus;

import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

import static com.vpaliy.xyzreader.ui.articles.ArticlesContract.Presenter;
import static dagger.internal.Preconditions.checkNotNull;

public class ArticlesFragment extends BaseFragment
        implements ArticlesContract.View {

    private Presenter presenter;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout refresher;

    @BindView(R.id.articles_list)
    protected RecyclerView articleList;

    @Inject
    protected RxBus rxBus;

    private ArticlesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_articles,container,false);
        bindLayout(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new ArticlesAdapter(getContext(),rxBus);
            articleList.setAdapter(adapter);
            refresher.setOnRefreshListener(()->presenter.refresh());
            presenter.start();
        }
    }

    @Override
    public void showList(List<Article> articles) {
        checkNotNull(articles);
        adapter.setData(articles);
    }

    @Override
    public void showEmptyMessage() {
        showMessage(getString(R.string.app_name));
    }

    @Override
    public void showErrorMessage() {
        showMessage(getString(R.string.app_name));
    }

    private void showMessage(String message){
        if(getView()!=null){
            Snackbar.make(getView(),message, getResources()
                    .getInteger(R.integer.message_duration));
        }
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        refresher.setRefreshing(isLoading);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        checkNotNull(presenter);
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void injectDependencies() {
        DaggerViewComponent.builder()
                .appComponent(App.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }
}
