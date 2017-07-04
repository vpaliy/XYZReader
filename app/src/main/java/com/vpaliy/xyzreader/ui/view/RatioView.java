package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.vpaliy.xyzreader.R;

public class RatioView extends View {

    //16:9
    private float ratio =.5625f;

    public RatioView(Context context){
        this(context,null);
    }

    public RatioView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public RatioView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        if(attrs!=null){
            TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RatioView);
            this.ratio=array.getFloat(R.styleable.RatioView_ratio,ratio);
            array.recycle();
        }
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth*ratio));
    }
}
