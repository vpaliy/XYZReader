package com.vpaliy.xyzreader.ui.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ColorTransition extends Transition {

    private static final String PROPNAME_COLOR = "com.vpaliy.xyzreader.ui.view:ColorTransition:backgroundColor";

    private static final String[] transitionProperties = {
            PROPNAME_COLOR
    };

    public ColorTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return transitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues){
        View view=transitionValues.view;
        if(view!=null) {
            Drawable drawable=view.getBackground();
            if(drawable!=null && drawable instanceof ColorDrawable){
                ColorDrawable colorDrawable=ColorDrawable.class.cast(drawable);
                transitionValues.values.put(PROPNAME_COLOR,colorDrawable);
            }
        }
    }

    private ColorDrawable getColor(TransitionValues values){
        return ColorDrawable.class.cast(values.view.getBackground());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        ColorDrawable colorFrom = getColor(startValues);
        ColorDrawable colorTo = getColor(endValues);
        return ObjectAnimator.ofObject(endValues.view, "backgroundColor", new ArgbEvaluator(),
                colorFrom.getColor(),colorTo.getColor());
    }

}