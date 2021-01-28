package com.ourfancyteamname.officespace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;
import com.ourfancyteamname.officespace.services.ProcessPackageService;

@RestController
@RequestMapping("/api/prc-pkg")
public class ProcessPackageController {

  @Autowired
  private ProcessPackageService processPackageService;

  @PostMapping("/add")
  @Transactional
  public ResponseEntity<Void> addPkgToCltNode(@RequestBody ProcessPackageDto processPackageDto) {
    processPackageService.addPkgToCltNode(processPackageDto);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<ProcessListView>> getValidPksToAdd(@PathVariable("id") Integer clusterNodeId) {
    return ResponseEntity.ok(processPackageService.getValidPksToAdd(clusterNodeId));
  }

}
