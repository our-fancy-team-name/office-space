package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.annotations.CanEditProduct;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/package")
public class PackageController {

  @Autowired
  private PackageService packageService;

  @PostMapping("/create")
  @Transactional
  @CanEditProduct
  public ResponseEntity<Void> create(@RequestBody PackageDto packageDto) {
    packageService.create(packageDto);
    return ResponseEntity.ok().build();
  }
}
