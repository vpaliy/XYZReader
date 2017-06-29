package com.vpaliy.xyzreader.ui.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaliy.xyzreader.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextContentAdapter extends RecyclerView.Adapter<TextContentAdapter.ViewHolder> {

    private List<String> data;
    private LayoutInflater inflater;

    public TextContentAdapter(Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.layout_content,parent,false);
        return new ViewHolder(root);
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
