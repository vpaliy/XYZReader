package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.base.BaseActivity;

public class ArticlesActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        if(savedInstanceState==null) {
            buildUI();
        }
    }

    private void buildUI(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame,new ArticlesFragment())
                .commit();
    }

    @Override
    public void injectDependencies() {
        App.app().component().inject(this);
    }
}
