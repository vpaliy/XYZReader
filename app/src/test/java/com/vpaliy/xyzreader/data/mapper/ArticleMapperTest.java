package com.vpaliy.xyzreader.data.mapper;

import com.vpaliy.xyzreader.FakeProvider;
import com.vpaliy.xyzreader.data.ArticleEntity;
import com.vpaliy.xyzreader.domain.Article;
import org.junit.runners.JUnit4;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

@RunWith(JUnit4.class)
public class ArticleMapperTest {

    private ArticleMapper mapper=new ArticleMapper();

    @Test
    public void mapsFakeEntityToReal(){
        ArticleEntity entity= FakeProvider.provideArticleEntity();
        Article article=mapper.map(entity);
        assertThatAreEqual(article,entity);
    }

    @Test
    public void mapsFakeEntityListToRealList(){
        List<ArticleEntity> entities=FakeProvider.provideArticleEntityList();
        List<Article> articles=mapper.map(entities);

        assertThat(articles,notNullValue());
        assertThat(articles.size(),is(entities.size()));

        for(int index=0;index<articles.size();index++){
            assertThatAreEqual(articles.get(index),entities.get(index));
        }
    }

    private void assertThatAreEqual(Article article, ArticleEntity entity){
        assertThat(article.getId(),is(entity.getId()));
        assertThat(article.getAuthor(),is(entity.getAuthor()));
        assertThat(article.getBackdropUrl(),is(entity.getBackdropUrl()));
        assertThat(article.getBody(),is(entity.getBody()));
        assertThat(article.getPosterUrl(),is(entity.getPosterUrl()));
        assertThat(article.getPublishedDate(),is(entity.getPublishedDate()));
        assertThat(article.getTitle(),is(entity.getTitle()));
    }
}
