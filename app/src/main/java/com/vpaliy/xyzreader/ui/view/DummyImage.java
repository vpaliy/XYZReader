package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


public class DummyImage extends AppCompatImageView {

    public DummyImage(Context context){
        super(context);
    }

    public DummyImage(Context context, AttributeSet attrs){
        super(context,attrs,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth*0.8f));
    }
}
