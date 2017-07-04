package com.vpaliy.xyzreader.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DeparallaxingChangeBounds extends ChangeBounds {

    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";

    public DeparallaxingChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        if (!(transitionValues.view instanceof RatioImageView)) return;
        RatioImageView psv = ((RatioImageView) transitionValues.view);
        if (psv.getOffset() == 0) return;

        // as we're going to remove the offset (which drives the parallax) we need to
        // compensate for this by adjusting the target bounds.
        Rect bounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        bounds.offset(0, psv.getOffset());
        transitionValues.values.put(PROPNAME_BOUNDS, bounds);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues,
                                   TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null
                || !(endValues.view instanceof RatioImageView)) return changeBounds;
        RatioImageView psv = ((RatioImageView) endValues.view);
        if (psv.getOffset() == 0) return changeBounds;

        Animator deparallax = ObjectAnimator.ofInt(psv, RatioImageView.OFFSET, 0);
        AnimatorSet transition = new AnimatorSet();
        transition.playTogether(changeBounds, deparallax);
        return transition;
    }
}