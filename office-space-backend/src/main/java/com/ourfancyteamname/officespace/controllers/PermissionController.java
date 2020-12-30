package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @GetMapping("/{role}")
  public ResponseEntity<List<PermissionDto>> getPermissionByRole(@PathVariable("role") String role) {
    return ResponseEntity.ok(permissionService.findAllPermissionByRole(role));
  }
}
