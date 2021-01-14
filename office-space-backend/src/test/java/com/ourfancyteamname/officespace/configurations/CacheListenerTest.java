package com.ourfancyteamname.officespace.configurations;

import org.ehcache.event.CacheEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CacheListenerTest {

  @Test
  public void onEvent() {
    CacheListener subject = new CacheListener();
    CacheEvent event = Mockito.mock(CacheEvent.class);
    subject.onEvent(event);
    Mockito.verify(event, Mockito.times(1)).getKey();
    Mockito.verify(event, Mockito.times(1)).getType();
    Mockito.verify(event, Mockito.times(1)).getOldValue();
    Mockito.verify(event, Mockito.times(1)).getNewValue();
    Mockito.verifyNoMoreInteractions(event);
  }
}
