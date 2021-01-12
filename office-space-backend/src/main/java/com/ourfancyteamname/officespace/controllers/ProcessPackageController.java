package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ProcessPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prc-pkg")
public class ProcessPackageController {

  @Autowired
  private ProcessPackageService processPackageService;

  @PostMapping("/list-view")
  public ResponseEntity<Page<ProcessListView>> getProcessListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(null);
  }

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
