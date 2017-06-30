package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.base.BaseActivity;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;
import com.vpaliy.xyzreader.ui.view.ActionBarUtils;
import butterknife.ButterKnife;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import butterknife.BindView;

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
        setActionBar();
    }

    private void setActionBar(){
        ActionBarUtils.fixStatusBarHeight(actionBar);
        setSupportActionBar(actionBar);
    }

    private void buildUI(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,new ArticlesFragment())
                .commit();
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
