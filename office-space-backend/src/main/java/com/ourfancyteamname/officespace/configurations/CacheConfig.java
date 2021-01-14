package com.ourfancyteamname.officespace.configurations;

import com.ourfancyteamname.officespace.enums.CacheName;
import lombok.SneakyThrows;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class CacheConfig implements JCacheManagerCustomizer {

  @Override
  @SneakyThrows
  public void customize(CacheManager cacheManager) {
    XmlConfiguration xmlConfiguration = new XmlConfiguration(getClass().getResource("/ehcache.xml"));
    CacheConfigurationBuilder<Object, Object> defaultTemplate =
        xmlConfiguration.newCacheConfigurationBuilderFromTemplate(CacheName.TEMPLATE, Object.class, Object.class);
    Stream.of(CacheName.NAMES).forEach(createCache(cacheManager, defaultTemplate));
  }

  private Consumer<String> createCache(CacheManager cacheManager, CacheConfigurationBuilder<Object, Object> config) {
    return name -> cacheManager.createCache(name, Eh107Configuration.fromEhcacheCacheConfiguration(config));
  }
}
