package com.ourfancyteamname.officespace.rdf.converters;

import org.eclipse.rdf4j.model.IRI;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;

@Mapper
public interface RdfConverter {

  @Mapping(expression = "java(iri)", target = "iri")
  @Mapping(expression = "java(iri.getNamespace() + iri.getLocalName())", target = "name")
  RdfIriDisplayDto toDto(IRI iri);
}
