package com.vpaliy.xyzreader.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;

/**
 * Shared element transitions do not seem to like transitioning from a single view to two separate
 * views so we need to alter the ChangeBounds transition to compensate
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SharedImageEnter extends ChangeBounds {

    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";

    public SharedImageEnter (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        int width = ((View) transitionValues.values.get(PROPNAME_PARENT)).getWidth();
        Rect bounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        bounds.right = width;
        bounds.bottom = width * 3 / 4;
        transitionValues.values.put(PROPNAME_BOUNDS, bounds);
    }

}