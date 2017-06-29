package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.base.BaseActivity;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;
import com.vpaliy.xyzreader.ui.view.ActionBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesActivity extends BaseActivity{

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        if(savedInstanceState==null) {
            buildUI();
        }
    }

    private void buildUI(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,new ArticlesFragment())
                .commit();
        ViewGroup.MarginLayoutParams params= ViewGroup.MarginLayoutParams.class.cast(actionBar.getLayoutParams());
        params.topMargin= ActionBarUtils.getStatusBarHeight(getResources());
        setSupportActionBar(actionBar);
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
