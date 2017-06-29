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
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    //A text could be extremely large, so using a single TextView is not an option.
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
            bodyRecycler.setHasFixedSize(true);
        }
    }

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

    //TODO move to another package, it's not the responsibility of this fragment
    private List<String> splitString(String body){
        body= Html.fromHtml(body).toString();
        TextUtils.SimpleStringSplitter splitter=new TextUtils.SimpleStringSplitter('.');
        splitter.setString(body);
        List<String> list=new ArrayList<>();
        while(splitter.hasNext()) {
            StringBuilder builder = new StringBuilder();
            for(int index=0;index<10;index++){
                builder.append(splitter.next());
                builder.append('.');
                if(!splitter.hasNext()) break;
            }
            list.add(builder.toString());
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
                        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT){
                            image.setTransitionName(Integer.toString(articleId));
                            startTransition();
                        }
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
