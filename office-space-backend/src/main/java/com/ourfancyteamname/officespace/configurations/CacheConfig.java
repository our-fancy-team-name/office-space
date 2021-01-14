package com.ourfancyteamname.officespace.configurations;

import com.ourfancyteamname.officespace.enums.CacheName;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Configuration
public class CacheConfig {

  @Value("${cache.template.time-to-live:30}")
  private Integer timeToLive;

  @Value("${cache.template.resources.off-heap-size:10}")
  private Integer offHeapSize;

  @Value("${cache.template.resources.disk-size:20}")
  private Integer diskSize;

  @Value("${cache.template.resources.heap-entries:1000}")
  private Integer heapEntries;

  @Bean
  public JCacheManagerCustomizer cacheManagerCustomizer() {
    return this::customize;
  }

  private void customize(CacheManager cacheManager) {
    CacheConfigurationBuilder defaultConfig = defaultConfigBuilder();
    CacheEntryListenerConfiguration defaultListener = defaultListener();
    Stream.of(CacheName.USER_PRINCIPLE, CacheName.PERMISSIONS)
        .forEach(createCache(cacheManager, defaultConfig, defaultListener));
  }

  private CacheEntryListenerConfiguration defaultListener() {
    return new MutableCacheEntryListenerConfiguration<>(
        FactoryBuilder.factoryOf(CacheListener.class),
        null,
        true,
        false);
  }

  private ResourcePoolsBuilder defaultResourcePool() {
    return ResourcePoolsBuilder
        .heap(heapEntries)
        .offheap(offHeapSize, MemoryUnit.MB)
        .disk(diskSize, MemoryUnit.MB, true);
  }

  private CacheConfigurationBuilder<Object, Object> defaultConfigBuilder() {
    return CacheConfigurationBuilder
        .newCacheConfigurationBuilder(Object.class, Object.class, defaultResourcePool())
        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(timeToLive)));
  }

  private Consumer<String> createCache(CacheManager cacheManager, CacheConfigurationBuilder<Object, Object> config,
      CacheEntryListenerConfiguration<Object, Object> listenerConfiguration) {
    return name -> cacheManager.createCache(name, Eh107Configuration.fromEhcacheCacheConfiguration(config))
        .registerCacheEntryListener(listenerConfiguration);
  }
}
