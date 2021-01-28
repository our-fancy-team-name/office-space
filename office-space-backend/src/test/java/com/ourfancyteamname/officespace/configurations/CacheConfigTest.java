package com.ourfancyteamname.officespace.configurations;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvokeTime;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

import javax.cache.Cache;
import javax.cache.CacheManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    var cacheManager = mock(CacheManager.class);
    Cache<Object, Object> cache = mock(Cache.class);
    mockReturn(cacheManager.createCache(any(), any()), cache);
    var jCacheManagerCustomizer = cacheConfig.cacheManagerCustomizer();
    jCacheManagerCustomizer.customize(cacheManager);
    verifyInvokeTime(cacheManager, 2).createCache(any(), any());
    verifyInvokeTime(cache, 2).registerCacheEntryListener(any());
  }
}
