package com.vpaliy.xyzreader.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment{

    private Unbinder unbinder;

    public abstract void injectDependencies();

    public void bindLayout(View root){
        unbinder= ButterKnife.bind(this,root);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(unbinder!=null) {
            unbinder.unbind();
        };
    }
}
