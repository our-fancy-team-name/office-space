package com.ourfancyteamname.officespace.dtos;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdfDto<S extends Resource, V extends Value> {
  private S subject;
  private IRI predicate;
  private V object;
}
