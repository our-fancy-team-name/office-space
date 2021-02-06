package com.ourfancyteamname.officespace.services;

import java.util.List;

import com.ourfancyteamname.officespace.dtos.RdfCreateDto;
import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface RdfService {

  List<RdfIriDisplayDto> getDefinedIRLs(TableSearchRequest tableSearchRequest);

  List<RdfIriDisplayDto> getDefinedIRLs();

  List<String> getDefinedNamespace();

  void create(RdfCreateDto rdfCreateDto);
}
