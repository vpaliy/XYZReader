package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.vpaliy.xyzreader.R;

public class RatioImageView extends AppCompatImageView {

    //16:9
    private float imageRatio=.5625f;

    public RatioImageView(Context context){
        this(context,null,0);
    }

    public RatioImageView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        if(attrs!=null){
            TypedArray array=getContext().obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            imageRatio=array.getFloat(R.styleable.RatioImageView_image_ration,imageRatio);
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth*imageRatio));
    }

    public void setImageRatio(float imageRatio) {
        this.imageRatio = imageRatio;
        requestLayout();
    }
}
