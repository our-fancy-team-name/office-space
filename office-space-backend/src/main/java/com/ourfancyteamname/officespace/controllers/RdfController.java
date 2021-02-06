package com.ourfancyteamname.officespace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.dtos.RdfCreateDto;
import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.RdfService;

@RestController
@RequestMapping("/api/rdf")
public class RdfController {

  @Autowired
  private RdfService rdfService;

  @PostMapping("/iris/search")
  public ResponseEntity<List<RdfIriDisplayDto>> getDefinedIRLs(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(rdfService.getDefinedIRLs(tableSearchRequest));
  }

  @GetMapping("/iris")
  public ResponseEntity<List<RdfIriDisplayDto>> getDefinedIRLsNoFilter() {
    return ResponseEntity.ok(rdfService.getDefinedIRLs());
  }

  @GetMapping("/namespace")
  public ResponseEntity<List<String>> getDefinedNamespace() {
    return ResponseEntity.ok(rdfService.getDefinedNamespace());
  }

  @PostMapping("/create")
  public ResponseEntity<Void> create(@RequestBody RdfCreateDto rdfCreateDto) {
    rdfService.create(rdfCreateDto);
    return ResponseEntity.ok().build();
  }

}
