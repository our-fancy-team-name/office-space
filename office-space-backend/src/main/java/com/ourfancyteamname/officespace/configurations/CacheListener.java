package com.ourfancyteamname.officespace.configurations;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryRemovedListener;
import javax.cache.event.CacheEntryUpdatedListener;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheListener implements
    CacheEntryCreatedListener<Object, Object>, CacheEntryExpiredListener<Object, Object>,
    CacheEntryRemovedListener<Object, Object>, CacheEntryUpdatedListener<Object, Object> {

  @Override
  public void onCreated(Iterable<CacheEntryEvent<?, ?>> iterable) {
    iterable.forEach(this::onEvent);
  }

  @Override
  public void onExpired(Iterable<CacheEntryEvent<?, ?>> iterable) {
    iterable.forEach(this::onEvent);
  }

  @Override
  public void onRemoved(Iterable<CacheEntryEvent<?, ?>> iterable) {
    iterable.forEach(this::onEvent);
  }

  @Override
  public void onUpdated(Iterable<CacheEntryEvent<?, ?>> iterable) {
    iterable.forEach(this::onEvent);
  }

  private void onEvent(CacheEntryEvent<?, ?> cacheEvent) {
    log.debug("Key: {} | EventType: {} | Old value: {} | New value: {}",
        cacheEvent.getKey(),
        cacheEvent.getEventType(),
        StringUtils.abbreviate(cacheEvent.getOldValue() + "", 50),
        StringUtils.abbreviate(cacheEvent.getValue() + "", 50));
  }
}
