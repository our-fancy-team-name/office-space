package com.ourfancyteamname.officespace.configurations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import javax.cache.Cache;
import javax.cache.CacheManager;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CacheConfigTest {

  @InjectMocks
  private CacheConfig cacheConfig;

  @Before
  public void setConfigValue() {
    ReflectionTestUtils.setField(cacheConfig, "heapEntries", 1000);
    ReflectionTestUtils.setField(cacheConfig, "timeToLive", 30);
    ReflectionTestUtils.setField(cacheConfig, "offHeapSize", 10);
    ReflectionTestUtils.setField(cacheConfig, "diskSize", 20);
  }

  @Test
  public void cacheManagerCustomizer() {
    CacheManager cacheManager = Mockito.mock(CacheManager.class);
    Cache cache = Mockito.mock(Cache.class);
    Mockito.when(cacheManager.createCache(Mockito.any(), Mockito.any())).thenReturn(cache);
    JCacheManagerCustomizer jCacheManagerCustomizer = cacheConfig.cacheManagerCustomizer();
    jCacheManagerCustomizer.customize(cacheManager);
    Mockito.verify(cacheManager, Mockito.times(2)).createCache(Mockito.any(), Mockito.any());
    Mockito.verify(cache, Mockito.times(2)).registerCacheEntryListener(Mockito.any());
  }
}
