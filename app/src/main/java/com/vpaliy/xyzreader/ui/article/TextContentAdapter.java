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
import android.support.annotation.NonNull;
import butterknife.ButterKnife;

class TextContentAdapter extends RecyclerView.Adapter<TextContentAdapter.AbstractHolder> {

    private final static int BLANK=0;
    private final static int CONTENT=1;

    private List<String> data;
    private LayoutInflater inflater;

    private View blank;

    TextContentAdapter(Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
    }

    @Override
    public AbstractHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root;
        switch (viewType){
            case BLANK:
                if(blank==null){
                    blank=inflater.inflate(R.layout.blank,parent,false);
                }
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

    public View getBlank(){
        return blank;
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
