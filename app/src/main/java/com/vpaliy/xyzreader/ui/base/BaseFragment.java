package com.vpaliy.xyzreader.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment{

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        injectDependencies();
    }

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
