package com.ourfancyteamname.officespace.rdf.repos.impl;

import java.util.function.Function;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.util.Repositories;

import com.ourfancyteamname.officespace.rdf.repos.RdfRepository;

public abstract class AbstractRdfRepository implements RdfRepository {

  protected abstract Repository getRepo();

  @Override
  public void save(Model model) {
    Repositories.consume(getRepo(), r -> r.add(model));
  }

  @Override
  public <T> T tupleQuery(String query, Function<TupleQueryResult, T> processFunction) {
    return Repositories.tupleQuery(getRepo(), query, processFunction);
  }
}
