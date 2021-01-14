package com.ourfancyteamname.officespace.configurations;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheListener implements CacheEventListener<Object, Object> {

  @Override
  public void onEvent(CacheEvent<?, ?> cacheEvent) {
    log.debug("Key: {} | EventType: {} | Old value: {} | New value: {}",
        cacheEvent.getKey(),
        cacheEvent.getType(),
        StringUtils.abbreviate(cacheEvent.getOldValue() + "", 50),
        StringUtils.abbreviate(cacheEvent.getNewValue() + "", 50));
  }

}
