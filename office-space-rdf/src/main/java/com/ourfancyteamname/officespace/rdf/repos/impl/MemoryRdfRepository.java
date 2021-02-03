package com.ourfancyteamname.officespace.rdf.repos.impl;

import org.eclipse.rdf4j.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ourfancyteamname.officespace.rdf.repos.RdfRepository;

@Component(RdfRepository.MEMORY_REPO)
public class MemoryRdfRepository extends AbstractRdfRepository {

  @Autowired
  @Qualifier(RdfRepository.MEMORY_STORE)
  private Repository repository;

  @Override
  protected org.eclipse.rdf4j.repository.Repository getRepo() {
    return repository;
  }
}
