package com.vpaliy.xyzreader.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.vpaliy.xyzreader.data.ArticleEntity;

import static com.vpaliy.xyzreader.data.source.local.ArticleContract.ArticleColumns.*;

public class DatabaseUtils {

    public static ContentValues toValue(ArticleEntity entity){
        if(entity!=null) {
            ContentValues values = new ContentValues();
            values.put(ARTICLE_AUTHOR,entity.getAuthor());
            values.put(ARTICLE_TITLE, entity.getTitle());
            values.put(ARTICLE_BODY, entity.getBody());
            values.put(ARTICLE_POSTER, entity.getPosterUrl());
            values.put(ARTICLE_BACKDROP_URL, entity.getBackdropUrl());
            values.put(ARTICLE_PUBLISH_DATE, entity.getPublishedDate());
            return values;
        }
        return null;
    }

    public static ArticleEntity toEntity(Cursor cursor){
        if(cursor!=null){
            ArticleEntity entity=new ArticleEntity();
            entity.setId(cursor.getInt(cursor.getColumnIndex(ARTICLE_ID)));
            entity.setAuthor(cursor.getString(cursor.getColumnIndex(ARTICLE_AUTHOR)));
            entity.setBody(cursor.getString(cursor.getColumnIndex(ARTICLE_BODY)));
            entity.setPublishedDate(cursor.getString(cursor.getColumnIndex(ARTICLE_PUBLISH_DATE)));
            entity.setBackdropUrl(cursor.getString(cursor.getColumnIndex(ARTICLE_BACKDROP_URL)));
            entity.setPosterUrl(cursor.getString(cursor.getColumnIndex(ARTICLE_POSTER)));
            entity.setTitle(cursor.getString(cursor.getColumnIndex(ARTICLE_TITLE)));
            return entity;
        }
        return null;
    }
}
