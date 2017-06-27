package com.vpaliy.xyzreader.data.source.local;


import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = ArticleDatabase.DATABASE_VERSION)
public class ArticleDatabase {

    static final int DATABASE_VERSION=1;

    @Table(ArticleContract.ArticleColumns.class)
    public static final String ARTICLES="articles";
}
