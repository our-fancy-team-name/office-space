package com.ourfancyteamname.officespace.rdf.repos;

import java.util.function.Function;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.TupleQueryResult;

public interface RdfRepository {
  String MEMORY_STORE = "sailMemoryStore";
  String MEMORY_FILE_STORE = "sailMemoryFileStore";
  String NATIVE_STORE = "sailNativeStore";

  void save(Model model);

  <T> T tupleQuery(String query, Function<TupleQueryResult, T> processFunction);
}
