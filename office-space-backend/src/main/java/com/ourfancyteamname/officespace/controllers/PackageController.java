package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.annotations.CanEditPackage;
import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @CanEditPackage
  public ResponseEntity<Void> create(@RequestBody PackageDto packageDto) {
    packageService.create(packageDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list-view")
  public ResponseEntity<Page<PackageListView>> getListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(packageService.getListView(tableSearchRequest));
  }

  @PatchMapping("/update")
  @Transactional
  @CanEditPackage
  public ResponseEntity<Void> update(@RequestBody PackageDto packageDto) {
    packageService.update(packageDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  @CanEditPackage
  public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
    packageService.delete(id);
    return ResponseEntity.ok().build();
  }
}
