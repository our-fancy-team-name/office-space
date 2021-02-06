package com.ourfancyteamname.officespace.dtos;

import org.eclipse.rdf4j.model.IRI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdfIriDisplayDto {
  private String name;
  private IRI iri;
}
