package com.ourfancyteamname.officespace.configurations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.cache.event.CacheEntryEvent;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class CacheListenerTest {

  public static final CacheListener subject = new CacheListener();

  @Mock
  private CacheEntryEvent<Object, Object> cacheEntryEvent;

  @Test
  void onCreated() {
    subject.onCreated(Collections.singletonList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  void onExpired() {
    subject.onExpired(Collections.singletonList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  void onRemoved() {
    subject.onRemoved(Collections.singletonList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  @Test
  void onUpdated() {
    subject.onUpdated(Collections.singletonList(cacheEntryEvent));
    verify(cacheEntryEvent);
  }

  private void verify(CacheEntryEvent<Object, Object> cacheEntryEvent) {
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getKey();
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getOldValue();
    Mockito.verify(cacheEntryEvent, Mockito.times(1)).getValue();
  }
}
