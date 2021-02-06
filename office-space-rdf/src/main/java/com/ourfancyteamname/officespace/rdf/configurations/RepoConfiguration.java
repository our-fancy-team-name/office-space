package com.ourfancyteamname.officespace.rdf.configurations;


import java.io.File;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ourfancyteamname.officespace.rdf.repos.RdfRepository;

@Configuration
public class RepoConfiguration {

  @Bean
  @Qualifier(RdfRepository.NATIVE_STORE)
  public Repository getNativeStore() {
    File dataNative = new File("temp/rdf/native");
    return new SailRepository(new NativeStore(dataNative));
  }

  @Bean
  @Qualifier(RdfRepository.MEMORY_FILE_STORE)
  public Repository getMemoryFileStore() {
    File dataDir = new File("temp/rdf/memoryFile");
    return new SailRepository(new MemoryStore(dataDir));
  }

  @Bean
  @Qualifier(RdfRepository.MEMORY_STORE)
  public Repository getMemoryStore() {
    return new SailRepository(new MemoryStore());
  }

}
