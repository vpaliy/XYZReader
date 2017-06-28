package com.vpaliy.xyzreader.data.mapper;

import android.util.Log;

import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.domain.Article;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArticleMapper extends Mapper<Article,ArticleEntity>  {

    @Inject
    public ArticleMapper(){}

    @Override
    public Article map(ArticleEntity fake) {
        Article article=new Article();
        article.setId(fake.getId());
        article.setAuthor(fake.getAuthor());
        article.setBackdropUrl(fake.getBackdropUrl());
        article.setPosterUrl(fake.getPosterUrl());
        article.setTitle(fake.getTitle());
        article.setPublishedDate(fake.getPublishedDate());
        article.setBody(fake.getBody());
        return article;
    }
}
