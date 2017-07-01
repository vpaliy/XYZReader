package com.vpaliy.xyzreader.ui.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

public class ActionBarUtils {

    public static int fixStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getDominantColor(Palette palette){
        if (palette != null) {
            Palette.Swatch result=palette.getDominantSwatch();
            if(palette.getDarkVibrantSwatch()!=null){
                result=palette.getDarkVibrantSwatch();
            }
            else if(palette.getDarkMutedSwatch()!=null){
                result=palette.getDarkMutedSwatch();
            }
            return result.getRgb();
        }
        return Color.WHITE;
    }

    public static int getSecondaryColor(Palette palette){
        if(palette!=null){
            Palette.Swatch lightVibrantSwatch   = palette.getLightVibrantSwatch();
            Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();

            Palette.Swatch tabBackground=lightMutedSwatch!=null?lightMutedSwatch
                    :(lightVibrantSwatch!=null?lightVibrantSwatch:palette.getDominantSwatch());
            return tabBackground.getRgb();
        }
        return Color.WHITE;
    }
}
