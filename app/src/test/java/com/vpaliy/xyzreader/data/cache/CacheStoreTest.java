package com.vpaliy.xyzreader.data.cache;

import android.os.Build;

import com.vpaliy.xyzreader.BuildConfig;
import com.vpaliy.xyzreader.FakeProvider;
import com.vpaliy.xyzreader.domain.Article;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        manifest = Config.DEFAULT_MANIFEST,
        sdk = Build.VERSION_CODES.LOLLIPOP)
public class CacheStoreTest {

    private static final int INIT_SIZE=10;
    private CacheStore<Article> cacheStore;

    @Before
    public void setUp(){
        cacheStore=new CacheStore<>(INIT_SIZE);
    }

    @Test
    public void putsValueIntoCache(){
        Article article=FakeProvider.provideArticle();
        cacheStore.put(FakeProvider.FAKE_ID,article);
        assertThat(cacheStore.get(FakeProvider.FAKE_ID),is(article));
    }

    @Test
    public void returnsNullIfThereIsNoValueInCache(){
        assertThat(cacheStore.get(FakeProvider.FAKE_ID),nullValue());
    }

    @Test
    public void replacesOneValueWithAnotherIfTheyHaveSameKey(){
        Article article=putIn();
        Article newArticle=FakeProvider.provideArticle();
        cacheStore.put(FakeProvider.FAKE_ID,newArticle);

        newArticle=cacheStore.get(FakeProvider.FAKE_ID);
        assertThat(article,not(newArticle));
    }

    @Test
    public void checksIfItemIsInCache(){
        putIn();
        assertTrue(cacheStore.isInCache(FakeProvider.FAKE_ID));
    }

    private Article putIn(){
        Article article= FakeProvider.provideArticle();
        cacheStore.put(FakeProvider.FAKE_ID,article);
        return article;
    }
}
