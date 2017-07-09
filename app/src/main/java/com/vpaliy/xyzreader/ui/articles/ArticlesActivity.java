package com.vpaliy.xyzreader.ui.articles;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import com.vpaliy.xyzreader.App;
import com.vpaliy.xyzreader.R;
import com.vpaliy.xyzreader.ui.base.BaseActivity;
import com.vpaliy.xyzreader.ui.base.bus.event.NavigationEvent;
import com.vpaliy.xyzreader.ui.view.PresentationUtils;
import butterknife.ButterKnife;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;

import static com.vpaliy.xyzreader.ui.articles.IArticlesConfig.ViewConfig;

public class ArticlesActivity extends BaseActivity{

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.drawer) @Nullable
    protected DrawerLayout drawer;

    @Inject
    protected IArticlesConfig articlesConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        setActionBar();
        setUpDrawer();
    }

    private void setUpDrawer(){
        //is null only for sw500dp
        if(drawer!=null){
            drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

            NavigationView navigationView=ButterKnife.findById(this,R.id.navigation);
            switch (articlesConfig.fetchConfig()){
                case GRID:
                    navigationView.setCheckedItem(R.id.as_grid);
                    break;
                case LIST:
                    navigationView.setCheckedItem(R.id.as_list);
                    break;
            }
            navigationView.setNavigationItemSelectedListener(item ->{
                drawer.closeDrawers();
                switch (item.getItemId()){
                    case R.id.as_list:
                        articlesConfig.save(ViewConfig.LIST);
                        return true;
                    case R.id.as_grid:
                        articlesConfig.save(ViewConfig.GRID);
                        return true;
                }
                return false;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(drawer!=null) {
            getMenuInflater().inflate(R.menu.articles_menu, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_view_type){
            if(drawer!=null) drawer.openDrawer(GravityCompat.END);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer!=null && drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
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
