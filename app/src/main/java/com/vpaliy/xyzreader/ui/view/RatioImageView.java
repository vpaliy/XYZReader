package com.vpaliy.xyzreader.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.vpaliy.xyzreader.R;

public class RatioImageView extends AppCompatImageView {

    //16:9
    private float imageRatio=.5625f;
    private float scrimAlpha = 0f;
    private float maxScrimAlpha = 1f;
    private int scrimColor = Color.TRANSPARENT;
    private final Paint scrimPaint;

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
            scrimAlpha=array.getFloat(R.styleable.RatioImageView_scrimAlpha,scrimAlpha);
            maxScrimAlpha=array.getFloat(R.styleable.RatioImageView_maxScrimAlpha,maxScrimAlpha);
            scrimColor=array.getColor(R.styleable.RatioImageView_scrimColor,scrimColor);
            array.recycle();
        }
        scrimPaint = new Paint();
        scrimPaint.setColor((scrimColor & 0x00ffffff) | ((int)(255f*scrimAlpha) << 24));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, Math.round(measuredWidth*imageRatio));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
    }

    public void setImageRatio(float imageRatio) {
        this.imageRatio = imageRatio;
        requestLayout();
    }
}
