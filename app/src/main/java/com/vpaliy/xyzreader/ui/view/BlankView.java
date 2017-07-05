package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.vpaliy.xyzreader.R;

/**
 * Lightweight view for filling gaps
 */
public class BlankView extends View {

    //16:9
    private float ratio =.5625f;
    private int staticHeight=0;

    public BlankView(Context context){
        this(context,null);
    }

    public BlankView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public BlankView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        if(attrs!=null){
            TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.BlankView);
            this.ratio=array.getFloat(R.styleable.BlankView_ratio,ratio);
            this.staticHeight=(int)(array.getDimension(R.styleable.BlankView_static_height,staticHeight));
            array.recycle();
        }
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }

    public void setStaticHeight(int staticHeight) {
        this.staticHeight = staticHeight;
    }

    public int getStaticHeight() {
        return staticHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth/ratio)+staticHeight);
    }
}
