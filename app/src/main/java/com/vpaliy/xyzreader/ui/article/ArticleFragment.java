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
import com.vpaliy.xyzreader.ui.view.ActionBarUtils;
import com.vpaliy.xyzreader.ui.view.RatioImageView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleFragment extends BaseFragment
        implements ArticleContract.View{

    private Presenter presenter;
    private TextContentAdapter adapter;
    private int articleId;

    @BindView(R.id.article_image)
    protected RatioImageView image;

    @BindView(R.id.background)
    protected View background;

    @BindView(R.id.actionBar)
    protected Toolbar toolbar;

    @BindView(R.id.floatingActionButton)
    @Nullable
    protected FloatingActionButton actionButton;

    @BindView(R.id.details)
    protected RecyclerView details;

    private View descriptionLayout;

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
        View root=inflater.inflate(R.layout.dummy,container,false);
        bindLayout(root);
        ViewCompat.setTransitionName(image,getString(R.string.poster_transition)+articleId);
        getActivity().supportPostponeEnterTransition();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            descriptionLayout=getActivity().getLayoutInflater().inflate(R.layout.blank, details,false);
            adapter=new TextContentAdapter(getContext(),descriptionLayout);
            details.setAdapter(adapter);
            details.setHasFixedSize(true);
            details.addOnScrollListener(listener);
            details.setOnFlingListener(flingListener);
            setUpActionBar();
            presenter.loadArticle(articleId);
        }
    }

    private void setUpActionBar(){
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(view->
                getActivity().supportFinishAfterTransition());
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
            final int scrollY= descriptionLayout.getTop();
            image.setOffset(scrollY);
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
        TextView text= ButterKnife.findById(descriptionLayout,R.id.article_author);
        text.setText(article.getAuthor());
        text=ButterKnife.findById(descriptionLayout,R.id.article_date);
        text.setText(article.getFormattedDate());
        text=ButterKnife.findById(descriptionLayout,R.id.article_title);
        text.setText(article.getTitle());
        new Handler().post(()->adapter.setData(article.getSplitBody()));
        /*actionButton.setOnClickListener(v->
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(article.getBody())
                        .getIntent(), getString(R.string.action_share))));*/
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
                        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
                            startTransition();
                        }
                        new Palette.Builder(resource)
                                .generate(ArticleFragment.this::applyPalette);
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startTransition(){
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().startPostponedEnterTransition();
                return true;
            }
        });
    }

    private void applyPalette(Palette palette){
        if (palette != null) {
          //  descriptionLayout.findViewById(R.id.blank).setBackgroundColor(ActionBarUtils.getDominantColor(palette));
            //descriptionLayout.findViewById(R.id.details_background).setBackgroundColor(ActionBarUtils.getDominantColor(palette));
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
