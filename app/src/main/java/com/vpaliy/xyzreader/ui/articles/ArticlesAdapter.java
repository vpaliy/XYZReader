package com.vpaliy.xyzreader.ui.articles;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.domain.Article;
import java.util.ArrayList;
import java.util.List;
import static android.support.v7.graphics.Palette.Swatch;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> data;
    private LayoutInflater inflater;

    ArticlesAdapter(Context context){
        this.data=new ArrayList<>();
        this.inflater=LayoutInflater.from(context);
    }

    public void setData(List<Article> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_image)
        ImageView image;

        @BindView(R.id.article_title)
        TextView articleTitle;

        @BindView(R.id.article_author)
        TextView articleAuthor;

        @BindView(R.id.background)
        View background;

        ViewHolder(View root){
            super(root);
            ButterKnife.bind(this,root);
        }

        void bindData(){
            Article article=at(getAdapterPosition());
            articleTitle.setText(article.getTitle());
            articleAuthor.setText(article.getAuthor());

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
                            new Palette.Builder(resource)
                                    .generate(ViewHolder.this::applyPalette);
                        }
                    });
        }

        private void applyPalette(Palette palette){
            if (palette != null) {
                Swatch result=palette.getDominantSwatch();
                if(palette.getDarkVibrantSwatch()!=null){
                    result=palette.getDarkVibrantSwatch();
                }
                else if(palette.getDarkMutedSwatch()!=null){
                    result=palette.getDarkMutedSwatch();
                }
                background.setBackgroundColor(result.getRgb());
            }
        }

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
