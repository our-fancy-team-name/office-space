package com.ourfancyteamname.officespace.rdf.repos;

import java.util.function.Function;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.TupleQueryResult;

public interface RdfRepository {

  void save(Model model);

  <T> T tupleQuery(String query, Function<TupleQueryResult, T> processFunction);
}
