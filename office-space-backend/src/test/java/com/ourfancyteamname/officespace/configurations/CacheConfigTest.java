package com.ourfancyteamname.officespace.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.cache.CacheManager;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CacheConfigTest {

  @InjectMocks
  private CacheConfig cacheConfig;

  @Test
  public void a() {
    CacheManager cacheManager = Mockito.mock(CacheManager.class);
    cacheConfig.customize(cacheManager);
    Mockito.verify(cacheManager, Mockito.times(2)).createCache(Mockito.any(), Mockito.any());
  }
}
