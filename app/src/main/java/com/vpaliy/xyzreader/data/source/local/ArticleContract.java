package com.vpaliy.xyzreader.data.source.local;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

public class ArticleContract {

    public static final String PATH_ARTICLE="articles";

    public interface ArticleColumns{
        @DataType(DataType.Type.INTEGER)
        @PrimaryKey
        String ARTICLE_ID="article_id";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_TITLE="article_title";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_BODY="article_body";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_POSTER="article_poster_url";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_BACKDROP_URL="article_backdrop_url";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_PUBLISH_DATE="article_publish_date";

        @DataType(DataType.Type.TEXT)
        String ARTICLE_AUTHOR="article_author";
    }
}
