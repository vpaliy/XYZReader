package com.vpaliy.xyzreader.ui.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

public class PresentationUtils {

    public static int fixStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int[] getPaletteColors(Palette palette){
        int colors[]=new int[2];
        colors[1]=-1;
        Palette.Swatch dominant=palette.getDominantSwatch();
        Palette.Swatch result=dominant;
        if(palette.getDarkVibrantSwatch()!=null){
            result=palette.getDarkVibrantSwatch();
        }
        if(palette.getDarkMutedSwatch()!=null){
            if(result!=dominant){
                colors[0]=result.getRgb();
                colors[1]=palette.getDarkMutedSwatch().getRgb();
                return colors;
            }
            result=palette.getDarkMutedSwatch();
        }
        colors[0]=dominant.getRgb();
        //check now
        if(result!=dominant){
            colors[1]=result.getRgb();
        }else{
            colors[1]=getSecondaryColor(palette);
        }
        return colors;

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
            int color=tabBackground.getRgb();
            float[] hsv=new float[3];
            Color.colorToHSV(color,hsv);
            hsv[2]*=0.5f;
            color=Color.HSVToColor(hsv);
            return color;
        }
        return Color.WHITE;
    }
}
