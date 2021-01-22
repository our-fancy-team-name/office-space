package com.ourfancyteamname.officespace.configurations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.test.util.ReflectionTestUtils;

import javax.cache.Cache;
import javax.cache.CacheManager;

@ExtendWith(MockitoExtension.class)
class CacheConfigTest {

  @InjectMocks
  private CacheConfig cacheConfig;

  @BeforeEach
  void setConfigValue() {
    ReflectionTestUtils.setField(cacheConfig, "heapEntries", 1000);
    ReflectionTestUtils.setField(cacheConfig, "timeToLive", 30);
    ReflectionTestUtils.setField(cacheConfig, "offHeapSize", 10);
    ReflectionTestUtils.setField(cacheConfig, "diskSize", 20);
  }

  @Test
  void cacheManagerCustomizer() {
    CacheManager cacheManager = Mockito.mock(CacheManager.class);
    Cache<Object, Object> cache = Mockito.mock(Cache.class);
    Mockito.when(cacheManager.createCache(Mockito.any(), Mockito.any())).thenReturn(cache);
    JCacheManagerCustomizer jCacheManagerCustomizer = cacheConfig.cacheManagerCustomizer();
    jCacheManagerCustomizer.customize(cacheManager);
    Mockito.verify(cacheManager, Mockito.times(2)).createCache(Mockito.any(), Mockito.any());
    Mockito.verify(cache, Mockito.times(2)).registerCacheEntryListener(Mockito.any());
  }
}
