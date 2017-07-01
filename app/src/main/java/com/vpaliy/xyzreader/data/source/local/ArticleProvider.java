package com.vpaliy.xyzreader.data.source.local;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

import static com.vpaliy.xyzreader.data.source.local.ArticleContract.PATH_ARTICLE;

@ContentProvider(authority = ArticleProvider.AUTHORITY,
                 database = ArticleDatabase.class)
public class ArticleProvider {

    public static final String AUTHORITY = "com.vpaliy.xyzreader";

    @TableEndpoint(table = ArticleDatabase.ARTICLES)
    public static class Articles{

        @ContentUri(path = PATH_ARTICLE, type = "vnd.android.cursor.dir/articles")
        public static Uri ARTICLES=Uri.parse("content://"+AUTHORITY+"/"+PATH_ARTICLE);

        @InexactContentUri(path=PATH_ARTICLE+"/#",
                name = ArticleContract.ArticleColumns.ARTICLE_ID,
                type = "vnd.android.cursor.dir/articles",
                whereColumn = ArticleContract.ArticleColumns.ARTICLE_ID,
                pathSegment = 1
        )
        public static Uri withId(long id) {
            return ARTICLES.buildUpon().appendPath(Long.toString(id)).build();
        }

    }
}
