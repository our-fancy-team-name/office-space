package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.eclipse.rdf4j.model.IRI;

public interface RdfService {

  List<IRI> getDefinedIRLs(String iri);

  List<IRI> getDefinedIRLs();
}
