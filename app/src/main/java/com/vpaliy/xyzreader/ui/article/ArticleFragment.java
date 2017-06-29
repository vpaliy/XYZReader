package com.vpaliy.xyzreader.ui.article;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.di.component.DaggerViewComponent;
import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.article.ArticleContract.Presenter;
import com.vpaliy.xyzreader.ui.base.BaseFragment;
import com.vpaliy.xyzreader.ui.base.Constants;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;

public class ArticleFragment extends BaseFragment
        implements ArticleContract.View{

    private Presenter presenter;
    private TextContentAdapter adapter;
    private int articleId;

    @BindView(R.id.article_image)
    protected ImageView image;

    @BindView(R.id.article_author)
    protected TextView articleAuthor;

    @BindView(R.id.article_title)
    protected TextView articleTitle;

    @BindView(R.id.article_date)
    protected TextView articleDate;

    @BindView(R.id.background)
    protected View background;

    @BindView(R.id.body_recycler)
    protected RecyclerView bodyRecycler;

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
            adapter=new TextContentAdapter(getContext());
            bodyRecycler.setAdapter(adapter);
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
        Time time = new Time();
        time.parse3339(article.getPublishedDate());
        articleTitle.setText(article.getTitle());
        articleAuthor.setText(article.getAuthor());
        articleDate.setText(DateUtils.getRelativeTimeSpanString(time.toMillis(false),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL).toString());
        loadImage(article.getBackdropUrl());
        adapter.setData(splitString(article.getBody()));
    }

    private List<String> splitString(String body){
        int size=body.length();
        int partSize=size/100;
        List<String> list=new ArrayList<>(partSize);
        int lastIndex=0;
        for(int index=partSize;index<size;index+=partSize){
            list.add(body.substring(lastIndex,index));
            lastIndex=index;
        }
        return list;
    }

    private void loadImage(String imageUrl){
        Glide.with(getContext())
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new ImageViewTarget<Bitmap>(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        image.setImageBitmap(resource);
                        new Palette.Builder(resource)
                                .generate(ArticleFragment.this::applyPalette);
                    }
                });
    }

    private void applyPalette(Palette palette){
        if (palette != null) {
            Palette.Swatch result=palette.getDominantSwatch();
            if(palette.getDarkVibrantSwatch()!=null){
                result=palette.getDarkVibrantSwatch();
            }
            else if(palette.getDarkMutedSwatch()!=null){
                result=palette.getDarkMutedSwatch();
            }
            background.setBackgroundColor(result.getRgb());
        }
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
