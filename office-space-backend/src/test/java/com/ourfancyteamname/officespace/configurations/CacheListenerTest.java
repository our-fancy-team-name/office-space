package com.ourfancyteamname.officespace.configurations;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;

import java.util.Collections;

import javax.cache.event.CacheEntryEvent;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class CacheListenerTest {

  private static final CacheListener subject = new CacheListener();


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
    verifyInvoke1Time(cacheEntryEvent).getKey();
    verifyInvoke1Time(cacheEntryEvent).getOldValue();
    verifyInvoke1Time(cacheEntryEvent).getValue();
  }
}
