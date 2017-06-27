package com.vpaliy.xyzreader;

import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.domain.Article;

import java.util.Arrays;
import java.util.List;

public class FakeProvider {
    public static final int FAKE_ID=1;
    public static final String FAKE_AUTHOR="fake_author";
    public static final String FAKE_TITLE="fake_title";
    public static final String FAKE_BODY="fake_body";
    public static final String FAKE_POSTER_URL="fake_poster_url";
    public static final String FAKE_BACKDROP_URL="fake_backdrop_url";
    public static final String FAKE_PUBLISHED_DATE="fake_published_date";

    public static final int LIST_SIZE=5;

    public static Article provideArticle(){
        Article article=new Article();
        article.setId(FAKE_ID);
        article.setAuthor(FAKE_AUTHOR);
        article.setTitle(FAKE_TITLE);
        article.setBody(FAKE_BODY);
        article.setPosterUrl(FAKE_POSTER_URL);
        article.setBackdropUrl(FAKE_BACKDROP_URL);
        article.setPublishedDate(FAKE_PUBLISHED_DATE);
        return article;
    }

    public static ArticleEntity provideArticleEntity(){
        ArticleEntity entity=new ArticleEntity();
        entity.setId(FAKE_ID);
        entity.setAuthor(FAKE_AUTHOR);
        entity.setTitle(FAKE_TITLE);
        entity.setBody(FAKE_BODY);
        entity.setPosterUrl(FAKE_POSTER_URL);
        entity.setAuthor(FAKE_BACKDROP_URL);
        entity.setPublishedDate(FAKE_PUBLISHED_DATE);
        return entity;
    }

    public static List<Article> provideArticleList(){
        return Arrays.asList(provideArticle(),provideArticle(),provideArticle(),
                provideArticle(),provideArticle());
    }

    public static List<ArticleEntity> provideArticleEntityList(){
        return Arrays.asList(provideArticleEntity(),provideArticleEntity(),
                provideArticleEntity(),provideArticleEntity(),provideArticleEntity());
    }
}
