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
import com.vpaliy.xyzreader.ui.view.ElasticDragDismissLayout;
import com.vpaliy.xyzreader.ui.view.FABToggle;
import com.vpaliy.xyzreader.ui.view.RatioImageView;
import com.vpaliy.xyzreader.ui.view.ReflowText;
import com.vpaliy.xyzreader.ui.view.TranslatableLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

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

    @BindView(R.id.details)
    protected RecyclerView details;

    @BindView(R.id.share_fab)
    protected FABToggle fabToggle;

    @BindView(R.id.details_background)
    protected TranslatableLayout articleDetailsLayout;

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
        bindAndPostpone();
        return root;
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

    private void bindAndPostpone(){
       // ViewCompat.setTransitionName(image,getString(R.string.poster_transition)+articleId);
        getActivity().supportPostponeEnterTransition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ElasticDragDismissLayout dragDismissLayout=ButterKnife.findById(getActivity(),R.id.draggable_frame);
        ElasticDragDismissLayout.SystemChromeFader fader=new ElasticDragDismissLayout.SystemChromeFader(getActivity()){
            @Override
            public void onDragDismissed() {
                getActivity().supportFinishAfterTransition();
            }
        };
        dragDismissLayout.addListener(fader);
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
                        .setText(article.getBody())
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
                        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
                            startTransition();
                        }
                        new Palette.Builder(resource)
                                .generate(ArticleFragment.this::applyPalette);
                        setFabLocation();
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
        fabToggle.setMinOffset(ViewCompat.getMinimumHeight(image)-fabToggle.getHeight()/2);
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
            articleDetailsLayout.setBackgroundColor(ActionBarUtils.getDominantColor(palette));
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
