package com.ourfancyteamname.officespace.dtos;

import java.io.Serial;
import java.io.Serializable;

import org.eclipse.rdf4j.model.IRI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdfIriDisplayDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -6101703694266327763L;
  private String name;
  private IRI iri;
}
