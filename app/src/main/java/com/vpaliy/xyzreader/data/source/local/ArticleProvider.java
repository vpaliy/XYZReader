package com.vpaliy.xyzreader.data.source.local;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import static com.vpaliy.xyzreader.data.source.local.ArticleContract.PATH_ARTICLE;

@ContentProvider(authority = ArticleProvider.AUTHORITY,
                 database = ArticleDatabase.class)
public class ArticleProvider {

    public static final String AUTHORITY = "com.vpaliy.espressoinaction.CoffeeProvider";

    @TableEndpoint(table = ArticleDatabase.ARTICLES)
    public static class Articles{

        @ContentUri(path = PATH_ARTICLE,
                type = "vnd.android.cursor.dir/coffees",
                defaultSort = ArticleContract.ArticleColumns.ARTICLE_ID+" ASC")
        public static Uri ARTICLES=Uri.parse("content://"+AUTHORITY+"/"+PATH_ARTICLE);

        public static Uri withId(long id) {
            return Uri.parse("content://" + AUTHORITY + "/"+PATH_ARTICLE+"/" + id);
        }

    }
}
