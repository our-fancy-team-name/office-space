package com.ourfancyteamname.officespace.rdf.repos.impl;

import org.eclipse.rdf4j.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ourfancyteamname.officespace.rdf.consts.QualifierName;

@Component
public class MemoryRdfRepository extends AbstractRdfRepository {

  @Autowired
  @Qualifier(QualifierName.MEMORY_STORE)
  private Repository repository;

  @Override
  protected org.eclipse.rdf4j.repository.Repository getRepo() {
    return repository;
  }
}
