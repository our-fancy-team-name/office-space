package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;
import com.ourfancyteamname.officespace.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

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
  public ResponseEntity<Void> addNodeToCluster(@RequestBody GraphDto graphDto) {
    processService.addNodeToCluster(graphDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/remove-node/{id}")
  @Transactional
  public ResponseEntity<Void> removeNodeFromCluster(@PathVariable("id") Integer clusterNodeId) {
    processService.removeNodeFromCluster(clusterNodeId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/edit-node")
  @Transactional
  public ResponseEntity<Void> editClusterNode(@RequestBody ClusterNodeEditDto clusterNodeEditDto) {
    processService.editClusterNode(clusterNodeEditDto);
    return ResponseEntity.ok().build();
  }
}
