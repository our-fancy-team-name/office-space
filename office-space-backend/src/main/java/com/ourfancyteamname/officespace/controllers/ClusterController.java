package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.annotations.CanEditCluster;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/cluster")
public class ClusterController {

  @Autowired
  private ClusterService clusterService;

  @PostMapping("/create")
  @Transactional
  @CanEditCluster
  public ResponseEntity<Void> create(@RequestBody ProcessGeneralDto processGeneralDto) {
    clusterService.create(processGeneralDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list-view")
  public ResponseEntity<Page<ProcessGeneralDto>> getListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(clusterService.getListView(tableSearchRequest));
  }

  @PatchMapping
  @Transactional
  @CanEditCluster
  public ResponseEntity<Void> update(@RequestBody ProcessGeneralDto processGeneralDto) {
    clusterService.update(processGeneralDto);
    return ResponseEntity.ok().build();
  }
}
