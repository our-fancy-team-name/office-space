package com.ourfancyteamname.officespace.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.cache.event.CacheEntryEvent;
import java.util.Arrays;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CacheListenerTest {

  public static final CacheListener subject = new CacheListener();

  @Test
  public void onCreated() {
    CacheEntryEvent cacheEntryEvent = Mockito.mock(CacheEntryEvent.class);
    subject.onCreated(Arrays.asList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  public void onExpired() {
    CacheEntryEvent cacheEntryEvent = Mockito.mock(CacheEntryEvent.class);
    subject.onExpired(Arrays.asList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  public void onRemoved() {
    CacheEntryEvent cacheEntryEvent = Mockito.mock(CacheEntryEvent.class);
    subject.onRemoved(Arrays.asList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  public void onUpdated() {
    CacheEntryEvent cacheEntryEvent = Mockito.mock(CacheEntryEvent.class);
    subject.onUpdated(Arrays.asList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  private void verify(CacheEntryEvent cacheEntryEvent) {
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getKey();
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getOldValue();
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getValue();
  }
}
