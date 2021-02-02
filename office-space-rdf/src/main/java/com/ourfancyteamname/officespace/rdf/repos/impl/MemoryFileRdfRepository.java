package com.ourfancyteamname.officespace.rdf.repos.impl;

import org.eclipse.rdf4j.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ourfancyteamname.officespace.rdf.consts.QualifierName;

@Component
public class MemoryFileRdfRepository extends AbstractRdfRepository {
  @Autowired
  @Qualifier(QualifierName.MEMORY_FILE_STORE)
  private Repository repository;

  @Override
  protected Repository getRepo() {
    return repository;
  }
}
