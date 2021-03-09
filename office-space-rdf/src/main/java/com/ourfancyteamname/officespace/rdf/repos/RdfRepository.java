package com.ourfancyteamname.officespace.rdf.repos;

import java.util.function.Function;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.TupleQueryResult;

public interface RdfRepository {
  String MEMORY_STORE = "sailMemoryStore";
  String MEMORY_FILE_STORE = "sailMemoryFileStore";
  String NATIVE_STORE = "sailNativeStore";
  String MEMORY_REPO = "sailMemoryRepo";
  String MEMORY_FILE_REPO = "sailMemoryFileRepo";
  String NATIVE_REPO = "sailNativeRepo";

  void save(Model model);

  void remove(Model model);

  <T> T tupleQuery(String query, Function<TupleQueryResult, T> processFunction);
}
