package com.vpaliy.xyzreader.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Keep;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BackgroundFade extends Visibility {

    public static final Property<Drawable, Integer> DRAWABLE_ALPHA
            = TransitionUtils.createIntProperty(new TransitionUtils.IntProp<Drawable>("alpha") {
        @Override
        public void set(Drawable drawable, int alpha) {
            drawable.setAlpha(alpha);
        }

        @Override
        public int get(Drawable drawable) {
            return drawable.getAlpha();
        }
    });
    public BackgroundFade() {
        super();
    }

    @Keep
    public BackgroundFade(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view,
                             TransitionValues startValues, TransitionValues endValues) {
        if (view == null || view.getBackground() == null) return null;
        Drawable background = view.getBackground();
        background.setAlpha(0);
        return ObjectAnimator.ofInt(background, DRAWABLE_ALPHA, 0, 255);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view,
                                TransitionValues startValues, TransitionValues endValues) {
        if (view == null || view.getBackground() == null) return null;
        return ObjectAnimator.ofInt(view.getBackground(), DRAWABLE_ALPHA, 0);
    }
}