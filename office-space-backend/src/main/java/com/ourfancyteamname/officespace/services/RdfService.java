package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.eclipse.rdf4j.model.IRI;

import com.ourfancyteamname.officespace.dtos.RdfDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface RdfService {

  List<IRI> getDefinedIRLs(TableSearchRequest tableSearchRequest);

  List<IRI> getDefinedIRLs();

  List<String> getDefinedNamespace();

  void create(RdfDto rdfDto);
}
