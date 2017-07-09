package com.vpaliy.xyzreader.ui.articles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.vpaliy.xyzreader.R;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArticleConfig implements IArticlesConfig {

    private SharedPreferences sh;
    private List<Callback> callbackList;

    private final String LIST_KEY;
    private final String GRID_KEY;
    private final String CONFIG_KEY;

    @Inject
    public ArticleConfig(Context context){
        this.sh= PreferenceManager.getDefaultSharedPreferences(context);
        this.LIST_KEY=context.getString(R.string.list_key);
        this.GRID_KEY=context.getString(R.string.grid_key);
        this.CONFIG_KEY=context.getString(R.string.config_key);
    }

    @Override
    public ViewConfig fetchConfig() {
        String result=sh.getString(CONFIG_KEY,LIST_KEY);
        if(result.equals(LIST_KEY)){
            return ViewConfig.LIST;
        }
        return ViewConfig.GRID;
    }

    @Override
    public void save(ViewConfig config) {
        switch (config){
            case GRID:
                sh.edit().putString(CONFIG_KEY,GRID_KEY).apply();
                break;
            case LIST:
                sh.edit().putString(CONFIG_KEY,LIST_KEY).apply();;
                break;
        }
        if(callbackList!=null){
            callbackList.forEach(callback -> callback.onConfigChanged(config));
        }
    }

    @Override
    public void subscribe(Callback callback) {
        if(callback!=null){
            if(callbackList==null) callbackList=new LinkedList<>();
            callbackList.add(callback);
        }
    }

    @Override
    public void unsubscribe(Callback callback) {
        if(callback!=null){
            if(callbackList!=null){
                callbackList.remove(callback);
            }
        }
    }
}
