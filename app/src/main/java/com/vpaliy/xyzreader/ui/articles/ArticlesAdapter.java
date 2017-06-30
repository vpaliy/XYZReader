package com.vpaliy.xyzreader.ui.articles;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.domain.Article;
import com.vpaliy.xyzreader.ui.base.bus.RxBus;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;
import com.vpaliy.xyzreader.ui.view.ActionBarUtils;

import java.util.ArrayList;
import java.util.List;
import static android.support.v7.graphics.Palette.Swatch;
import butterknife.ButterKnife;

import butterknife.BindView;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> data;
    private LayoutInflater inflater;
    private Handler handler;
    private RxBus rxBus;
    private boolean isLocked;

    ArticlesAdapter(Context context, RxBus rxBus){
        this.data=new ArrayList<>();
        this.handler=new Handler();
        this.rxBus=rxBus;
        this.inflater=LayoutInflater.from(context);
    }

    public void setData(List<Article> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.article_image)
        ImageView image;

        @BindView(R.id.article_title)
        TextView articleTitle;

        @BindView(R.id.article_author)
        TextView articleAuthor;

        @BindView(R.id.background)
        View background;

        @BindView(R.id.article_date)
        TextView articleDate;

        ViewHolder(View root){
            super(root);
            ButterKnife.bind(this,root);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //if the user clicks more than once
            if(!isLocked) {
                isLocked=true;
                Article article = at(getAdapterPosition());
                rxBus.send(NavigationEvent.navigate(image,article.getId()));
                //release after the details have been launched, 2000ms should be enough
                handler.postDelayed(ArticlesAdapter.this::unlock,2000);
            }
        }

        void bindData(){
            Article article=at(getAdapterPosition());
            Time time=new Time();
            time.parse3339(article.getPublishedDate());
            articleTitle.setText(article.getTitle());
            articleAuthor.setText(article.getAuthor());
            articleDate.setText(DateUtils.getRelativeTimeSpanString(time.toMillis(false),
                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL).toString());
            Glide.with(itemView.getContext())
                    .load(article.getPosterUrl())
                    .asBitmap()
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(new ImageViewTarget<Bitmap>(image) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            image.setImageBitmap(resource);
                            //color the background
                            new Palette.Builder(resource)
                                    .generate(ViewHolder.this::applyPalette);
                        }
                    });
        }

        private void applyPalette(Palette palette){
            background.setBackgroundColor(ActionBarUtils.getDominantColor(palette));
        }

    }

    private void unlock(){
        isLocked=!isLocked;
    }

    private Article at(int index){
        return data.get(index);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_article_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
