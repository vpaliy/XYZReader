package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RatioImageView extends AppCompatImageView {

    private float imageRatio=1.5f;

    public RatioImageView(Context context){
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, (int) (measuredWidth / imageRatio));
    }

    public void setImageRatio(float imageRatio) {
        this.imageRatio = imageRatio;
        requestLayout();
    }
}
