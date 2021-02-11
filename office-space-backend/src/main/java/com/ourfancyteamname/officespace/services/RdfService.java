package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.dtos.RdfCreateDto;
import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;
import com.ourfancyteamname.officespace.dtos.RdfObject;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface RdfService {

  Page<RdfIriDisplayDto> getDefinedIRLs(TableSearchRequest tableSearchRequest);

  List<RdfIriDisplayDto> getDefinedIRLs();

  List<String> getDefinedNamespace();

  void create(RdfCreateDto rdfCreateDto);

  void createIri(RdfObject rdfObject);

  List<RdfCreateDto> getAll();
}
