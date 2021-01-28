package com.ourfancyteamname.officespace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.annotations.CanEditProcess;
import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;
import com.ourfancyteamname.officespace.services.ProcessService;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

  @Autowired
  private ProcessService processService;

  @GetMapping("/{id}")
  public ResponseEntity<GraphDto> getByClusterId(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(processService.getGraph(id));
  }

  @PostMapping("/add-node")
  @Transactional
  @CanEditProcess
  public ResponseEntity<Void> addNodeToCluster(@RequestBody GraphDto graphDto) {
    processService.addNodeToCluster(graphDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remove-node/{id}")
  @Transactional
  @CanEditProcess
  public ResponseEntity<Void> removeNodeFromCluster(@PathVariable("id") Integer clusterNodeId) {
    processService.removeNodeFromCluster(clusterNodeId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/edit-node")
  @Transactional
  @CanEditProcess
  public ResponseEntity<Void> editClusterNode(@RequestBody ClusterNodeEditDto clusterNodeEditDto) {
    processService.editClusterNode(clusterNodeEditDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/add-path/{from}/{to}")
  @Transactional
  @CanEditProcess
  public ResponseEntity<Void> addPath(@PathVariable("from") Integer clusterNodeIdFrom,
      @PathVariable("to") Integer clusterNodeIdTo) {
    processService.addSinglePath(clusterNodeIdFrom, clusterNodeIdTo);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remove-path/{id}")
  @Transactional
  @CanEditProcess
  public ResponseEntity<Void> removePath(@PathVariable("id") Integer pathId) {
    processService.removePath(pathId);
    return ResponseEntity.ok().build();
  }
}
