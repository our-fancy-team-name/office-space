package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.eclipse.rdf4j.model.IRI;

import com.ourfancyteamname.officespace.dtos.RdfDto;

public interface RdfService {

  List<IRI> getDefinedIRLs(String iri);

  List<IRI> getDefinedIRLs();

  List<String> getDefinedNamespace();

  void create(RdfDto rdfDto);
}
