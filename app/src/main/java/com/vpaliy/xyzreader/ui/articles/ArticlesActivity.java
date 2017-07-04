package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.base.BaseActivity;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;
import com.vpaliy.xyzreader.ui.view.PresentationUtils;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.BindView;

public class ArticlesActivity extends BaseActivity{

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        setActionBar();
    }

    private void setActionBar(){
        int statusBarHeight= PresentationUtils.fixStatusBarHeight(getResources());
        actionBar.getLayoutParams().height+=statusBarHeight;
        actionBar.setPadding(0,statusBarHeight,0,0);
        setSupportActionBar(actionBar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        navigator.navigateToDetails(this, NavigationEvent.class.cast(event));
    }

    @Override
    public void injectDependencies() {
        App.app().component().inject(this);
    }
}
