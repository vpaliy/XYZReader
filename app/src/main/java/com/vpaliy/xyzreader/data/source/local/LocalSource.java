package com.vpaliy.xyzreader.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.data.source.DataSource;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import static com.vpaliy.xyzreader.data.source.local.ArticleProvider.Articles.ARTICLES;

@Singleton
public class LocalSource extends DataSource<ArticleEntity> {

    private ContentResolver contentResolver;

    @Inject
    public LocalSource(Context context){
        contentResolver=context.getContentResolver();
    }

    @Override
    public Observable<ArticleEntity> get(int id) {
        return Observable.fromCallable(()->{
            Cursor cursor=contentResolver.query(ArticleProvider.Articles.withId(id),null,null,null,null);
            return DatabaseUtils.toEntity(cursor);
        });
    }

    @Override
    public Observable<List<ArticleEntity>> get() {
        return Observable.fromCallable(()->{
            List<ArticleEntity> result=new ArrayList<>();
            Cursor cursor=contentResolver.query(ArticleProvider.Articles.ARTICLES,null,null,null,null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    result.add(DatabaseUtils.toEntity(cursor));
                }
                if(!cursor.isClosed()) cursor.close();
            }
            return result;
        });
    }

    @Override
    public void insert(ArticleEntity item) {
        ContentValues values=DatabaseUtils.toValue(item);
        if(values!=null){
            contentResolver.insert(ARTICLES,values);
        }
    }
}
