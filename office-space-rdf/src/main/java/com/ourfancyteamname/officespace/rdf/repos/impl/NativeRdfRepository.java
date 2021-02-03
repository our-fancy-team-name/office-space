package com.ourfancyteamname.officespace.rdf.repos.impl;

import org.eclipse.rdf4j.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ourfancyteamname.officespace.rdf.repos.RdfRepository;

@Component(RdfRepository.NATIVE_REPO)
public class NativeRdfRepository extends AbstractRdfRepository {

  @Autowired
  @Qualifier(RdfRepository.NATIVE_STORE)
  private Repository repository;

  @Override
  protected Repository getRepo() {
    return repository;
  }
}
