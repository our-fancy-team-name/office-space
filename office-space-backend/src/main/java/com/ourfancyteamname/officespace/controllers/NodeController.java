package com.ourfancyteamname.officespace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.annotations.CanEditNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.NodeService;

@RestController
@RequestMapping("/api/node")
public class NodeController {

  @Autowired
  private NodeService nodeService;

  @PostMapping("/create")
  @Transactional
  @CanEditNode
  public ResponseEntity<Void> create(@RequestBody ProcessGeneralDto processGeneralDto) {
    nodeService.create(processGeneralDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list-view")
  public ResponseEntity<Page<ProcessGeneralDto>> getListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(nodeService.getListView(tableSearchRequest));
  }

  @PatchMapping
  @Transactional
  @CanEditNode
  public ResponseEntity<Void> update(@RequestBody ProcessGeneralDto processGeneralDto) {
    nodeService.update(processGeneralDto);
    return ResponseEntity.ok().build();
  }
}
