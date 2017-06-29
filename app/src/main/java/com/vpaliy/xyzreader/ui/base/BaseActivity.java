package com.vpaliy.xyzreader.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vpaliy.xyzreader.ui.Navigator;
import com.vpaliy.xyzreader.ui.base.bus.RxBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected Navigator navigator;

    @Inject
    protected RxBus rxBus;

    protected CompositeDisposable disposables;

    public abstract void handleEvent(@NonNull Object event);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }


    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        disposables.add(rxBus.asFlowable()
                .subscribe(this::processEvent));
    }

    private void processEvent(Object object){
        if(object!=null){
            handleEvent(object);
        }
    }

    @CallSuper
    @Override
    protected void onStop(){
        super.onStop();
        disposables.clear();
    }

    public abstract void injectDependencies();
}
