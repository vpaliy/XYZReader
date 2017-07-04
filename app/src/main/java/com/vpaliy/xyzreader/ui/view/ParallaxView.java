package com.vpaliy.xyzreader.ui.view;


import android.util.Property;

public interface ParallaxView {
    int getOffset();
    void setOffset(int offset);

    Property<ParallaxView, Integer> OFFSET =
            TransitionUtils.createIntProperty(new TransitionUtils.IntProp<ParallaxView>("offset") {
                @Override
                public void set(ParallaxView view, int offset) {
                    view.setOffset(offset);
                }

                @Override
                public int get(ParallaxView view) {
                    return view.getOffset();
                }
            });
}
