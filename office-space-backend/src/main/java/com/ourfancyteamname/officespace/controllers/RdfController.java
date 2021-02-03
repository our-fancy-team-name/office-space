package com.ourfancyteamname.officespace.controllers;

import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.dtos.RdfDto;
import com.ourfancyteamname.officespace.services.RdfService;

@RestController
@RequestMapping("/api/rdf")
public class RdfController {

  @Autowired
  private RdfService rdfService;

  @GetMapping("/iris/{iri}")
  public ResponseEntity<List<IRI>> getDefinedIRLs(@PathVariable("iri") String iri) {
    return ResponseEntity.ok(rdfService.getDefinedIRLs(iri));
  }

  @GetMapping("/iris")
  public ResponseEntity<List<IRI>> getDefinedIRLsNoFilter() {
    return ResponseEntity.ok(rdfService.getDefinedIRLs());
  }

  @GetMapping("/namespace")
  public ResponseEntity<List<String>> getDefinedNamespace() {
    return ResponseEntity.ok(rdfService.getDefinedNamespace());
  }

  @PostMapping("/create")
  public ResponseEntity<Void> create(@RequestBody RdfDto<?, ?> rdfDto) {
    rdfService.create(rdfDto);
    return ResponseEntity.ok().build();
  }

}
