package com.vpaliy.xyzreader.ui.articles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.domain.Article;
import java.util.ArrayList;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> data;
    private LayoutInflater inflater;

    public ArticlesAdapter(Context context){
        this.data=new ArrayList<>();
        this.inflater=LayoutInflater.from(context);
    }

    public void setData(List<Article> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View root){
            super(root);
        }

        void bindData(){

        }
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
