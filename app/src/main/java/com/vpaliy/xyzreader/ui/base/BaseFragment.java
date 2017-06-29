package com.vpaliy.xyzreader.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.vpaliy.xyzreader.R;

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
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) {
            unbinder.unbind();
        }
    }

    protected void showMessage(String message){
        if(getView()!=null){
            Snackbar.make(getView(),message, getResources()
                    .getInteger(R.integer.message_duration));
        }
    }
}
