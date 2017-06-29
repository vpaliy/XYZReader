package com.vpaliy.xyzreader.ui.article;

import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.di.component.DaggerViewComponent;
import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.article.ArticleContract.Presenter;
import com.vpaliy.xyzreader.ui.base.BaseFragment;
import com.vpaliy.xyzreader.ui.base.Constants;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

public class ArticleFragment extends BaseFragment
        implements ArticleContract.View{

    private Presenter presenter;
    private int articleId;

    @BindView(R.id.article_image)
    protected ImageView image;

    @BindView(R.id.article_author)
    protected TextView articleAuthor;

    @BindView(R.id.article_title)
    protected TextView articleTitle;

    @BindView(R.id.article_body)
    protected TextView articleBody;

    @BindView(R.id.article_date)
    protected TextView articleDate;

    public static ArticleFragment newInstance(Bundle extras){
        ArticleFragment fragment=new ArticleFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void injectDependencies() {
        DaggerViewComponent.builder()
                .appComponent(App.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        articleId=savedInstanceState.getInt(Constants.EXTRA_ARTICLE_ID);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_article,container,false);
        bindLayout(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.loadArticle(articleId);
        }
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {

    }

    @Override
    public void showArticle(Article article) {
        articleAuthor.setText(article.getAuthor());
        articleBody.setText(article.getBody());
        articleTitle.setText(article.getTitle());
        loadImage(article.getBackdropUrl());
    }

    private void loadImage(String imageUrl){

    }

    @Override @Inject
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_ARTICLE_ID,articleId);
    }
}
