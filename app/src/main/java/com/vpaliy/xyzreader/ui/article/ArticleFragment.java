package com.vpaliy.xyzreader.ui.article;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.di.component.DaggerViewComponent;
import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.article.ArticleContract.Presenter;
import com.vpaliy.xyzreader.ui.base.BaseFragment;
import com.vpaliy.xyzreader.ui.base.Constants;
import com.vpaliy.xyzreader.ui.view.BlankView;
import com.vpaliy.xyzreader.ui.view.PresentationUtils;
import com.vpaliy.xyzreader.ui.view.ElasticDragDismissLayout;
import com.vpaliy.xyzreader.ui.view.FABToggle;
import com.vpaliy.xyzreader.ui.view.ParallaxRatioImageView;
import com.vpaliy.xyzreader.ui.view.TranslatableLayout;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;

public class ArticleFragment extends BaseFragment
        implements ArticleContract.View{

    private Presenter presenter;
    private TextContentAdapter adapter;
    private int articleId;

    @BindView(R.id.article_image)
    protected ParallaxRatioImageView image;

    @BindView(R.id.background)
    protected View background;

    @BindView(R.id.details)
    protected RecyclerView details;

    @BindView(R.id.back_wrapper)
    protected View backWrapper;

    @BindView(R.id.share_fab)
    protected FABToggle fabToggle;

    @BindView(R.id.details_background)
    protected TranslatableLayout articleDetailsLayout;

    @BindView(R.id.draggable_frame)
    protected ElasticDragDismissLayout dragDismissLayout;

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
        fader=new ElasticDragDismissLayout.SystemChromeFader(getActivity()){
            @Override
            public void onDragDismissed() {
                getActivity().supportFinishAfterTransition();
            }
        };
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
    public void onPause() {
        super.onPause();
        dragDismissLayout.removeListener(fader);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new TextContentAdapter(getContext());
            details.setAdapter(adapter);
            details.setHasFixedSize(true);
            details.addOnScrollListener(listener);
            details.setOnFlingListener(flingListener);
            setUpActionBar();
            presenter.loadArticle(articleId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dragDismissLayout.addListener(fader);
    }

    private void setUpActionBar(){
        backWrapper.setPadding(0, PresentationUtils.fixStatusBarHeight(getResources()),0,0);
    }

    @OnClick(R.id.back)
    public void returnBack(){
        getActivity().supportFinishAfterTransition();
    }

    private RecyclerView.OnFlingListener flingListener = new RecyclerView.OnFlingListener() {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            image.setImmediatePin(true);
            return false;
        }
    };

    private RecyclerView.OnScrollListener listener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            image.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
             final int scrollY= adapter.getBlank().getTop();
             image.setOffset(scrollY);
             articleDetailsLayout.setOffset(articleDetailsLayout.getStaticOffset()+scrollY);
             fabToggle.setOffset(fabToggle.getStaticOffset()+scrollY);
        }
    };

    @Override
    public void showErrorMessage() {
        showMessage(getString(R.string.error_message));
    }

    @Override
    public void showEmptyMessage() {
        showMessage(getString(R.string.empty_message));
    }

    @Override
    public void showArticle(Article article) {
        loadImage(article.getBackdropUrl());
        TextView text= ButterKnife.findById(articleDetailsLayout,R.id.article_author);
        text.setText(article.getAuthor());
        text=ButterKnife.findById(articleDetailsLayout,R.id.article_date);
        text.setText(article.getFormattedDate());
        text=ButterKnife.findById(articleDetailsLayout,R.id.article_title);
        text.setText(article.getTitle());
        new Handler().post(()->adapter.setData(article.getSplitBody()));
        fabToggle.setOnClickListener(v->
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(article.getTitle())
                        .getIntent(), getString(R.string.action_share))));
    }

    private void loadImage(String imageUrl){
        Glide.with(getContext())
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageViewTarget<Bitmap>(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        image.setImageBitmap(resource);
                        new Palette.Builder(resource)
                                .generate(ArticleFragment.this::applyPalette);
                        setFabLocation();
                        startTransition();
                    }
                });
    }

    private void setFabLocation(){
        int imageHeight=image.getHeight();
        int layoutHeight=articleDetailsLayout.getHeight();
        int offset=image.getHeight()+layoutHeight-(fabToggle.getHeight()/2);
        articleDetailsLayout.setOffset(imageHeight);
        articleDetailsLayout.setStaticOffset(imageHeight);
        fabToggle.setStaticOffset(offset);
        fabToggle.setOffset(offset);
        fabToggle.setMinOffset(layoutHeight-fabToggle.getHeight()/2);
        BlankView blankView=adapter.getBlank();
        blankView.setStaticHeight(layoutHeight+(int)getResources().getDimension(R.dimen.spacing_large));
        blankView.requestLayout();
        image.setMinimumHeight(layoutHeight);
    }

    private void startTransition(){
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });
    }

    private void applyPalette(Palette palette){
        if (palette != null) {
            int[] paletteColors= PresentationUtils.getPaletteColors(palette);
            articleDetailsLayout.setBackgroundColor(paletteColors[0]);
            fabToggle.setBackgroundTintList(ColorStateList.valueOf(paletteColors[1]));
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

    private ElasticDragDismissLayout.SystemChromeFader fader;
}
