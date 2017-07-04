package com.vpaliy.xyzreader.ui.article;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.di.component.DaggerViewComponent;
import com.vpaliy.xyzreader.di.module.PresenterModule;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.Constants;
import com.vpaliy.xyzreader.ui.view.ActionBarUtils;
import com.vpaliy.xyzreader.ui.view.DummyImage;
import com.vpaliy.xyzreader.ui.view.ElasticDragDismissLayout;
import com.vpaliy.xyzreader.ui.view.RatioImageView;
import com.vpaliy.xyzreader.ui.view.TransitionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Dummy extends AppCompatActivity
        implements ArticleContract.View{

    protected ArticleContract.Presenter presenter;

    private int articleId;

    private static final String DESCRIPTION_TAG="description";

    @BindView(R.id.details)
    RecyclerView details;

    @BindView(R.id.article_image)
    RatioImageView image;

    private View descriptionLayout;

    private TextContentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);
        injectDependencies();
        ButterKnife.bind(this);
        descriptionLayout =getLayoutInflater().inflate(R.layout.blank,details,false);
        this.articleId=getIntent().getExtras().getInt(Constants.EXTRA_ARTICLE_ID);
        ViewCompat.setTransitionName(image,getString(R.string.poster_transition)+articleId);
        supportPostponeEnterTransition();
        presenter.loadArticle(articleId);
        adapter=new TextContentAdapter(this, descriptionLayout);
        details.setAdapter(adapter);
        details.setHasFixedSize(true);
        details.addOnScrollListener(listener);
        details.setOnFlingListener(flingListener);

    }

    public void injectDependencies() {
        DaggerViewComponent.builder()
                .appComponent(App.app().component())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }


    @Override @Inject
    public void attachPresenter(@NonNull ArticleContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ElasticDragDismissLayout dragDismissLayout=ButterKnife.findById(this,R.id.draggable_frame);
        ElasticDragDismissLayout.SystemChromeFader fader=new ElasticDragDismissLayout.SystemChromeFader(this){
            @Override
            public void onDragDismissed() {
                supportFinishAfterTransition();
            }
        };
        dragDismissLayout.addListener(fader);
    }

    @Override
    public void showArticle(Article article) {
        Glide.with(this)
                .load(article.getPosterUrl())
                .asBitmap()
                .centerCrop()
                .into(new ImageViewTarget<Bitmap>(image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        image.setImageBitmap(resource);
                        if(Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT) {
                            startTransition();
                        }
                        new Palette.Builder(resource)
                                .generate(Dummy.this::applyPalette);
                    }
                });

        TextView text=ButterKnife.findById(descriptionLayout,R.id.article_author);
        text.setText(article.getAuthor());
        text=ButterKnife.findById(descriptionLayout,R.id.article_date);
        text.setText(article.getFormattedDate());
        text=ButterKnife.findById(descriptionLayout,R.id.article_title);
        text.setText(article.getTitle());
        new Handler().post(()->adapter.setData(article.getSplitBody()));
    }

    private void applyPalette(Palette palette){
        if (palette != null) {
            descriptionLayout.findViewById(R.id.blank).setBackgroundColor(ActionBarUtils.getDominantColor(palette));
            descriptionLayout.findViewById(R.id.details_background).setBackgroundColor(ActionBarUtils.getDominantColor(palette));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startTransition(){
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showErrorMessage() {

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

    class TextContentAdapter extends RecyclerView.Adapter<TextContentAdapter.AbstractHolder> {

        private final static int BLANK=0;
        private final static int CONTENT=1;

        private List<String> data;
        private LayoutInflater inflater;

        private View blank;

        TextContentAdapter(Context context, @NonNull View blank){
            this.inflater=LayoutInflater.from(context);
            this.data=new ArrayList<>();
            this.blank=blank;
        }

        @Override
        public AbstractHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root;
            switch (viewType){
                case BLANK:
                    return new BlankViewHolder(blank);
                default:
                    root=inflater.inflate(R.layout.layout_content,parent,false);
            }
            return new TextContentAdapter.ViewHolder(root);
        }

        public void setData(List<String> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(AbstractHolder holder, int position) {
            holder.bindData();
        }

        @Override
        public int getItemViewType(int position) {
            return position==0?BLANK:CONTENT;
        }

        @Override
        public int getItemCount() {
            return data.size()+1;
        }

        abstract class AbstractHolder extends RecyclerView.ViewHolder {
            AbstractHolder(View root){
                super(root);
                ButterKnife.bind(this,root);
            }
           abstract void bindData();
        }

        class BlankViewHolder extends AbstractHolder{
            BlankViewHolder(View itemView){
                super(itemView);
            }
            @Override void bindData() {}
        }

        class ViewHolder extends AbstractHolder {

            @BindView(R.id.article_body)
            TextView content;

            ViewHolder(View root){
                super(root);
                ButterKnife.bind(this,root);
            }

            void bindData(){
                content.setText(data.get(getAdapterPosition()));
            }
        }
    }

}
