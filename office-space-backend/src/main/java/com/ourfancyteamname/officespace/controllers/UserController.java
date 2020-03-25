package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.postgres.entities.User;
import com.ourfancyteamname.officespace.postgres.repos.UserRepository;
import com.ourfancyteamname.officespace.postgres.specification.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private List<SpecificationService> specificationServices;

  @PostMapping
  public ResponseEntity<List<User>> getAll(@RequestBody @Valid TableSearchRequest tableSearchRequest) {
    Specification specs = specificationServices.stream()
        .filter(service -> service.match(tableSearchRequest.getTableName()))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new)
        .specificationBuilder(tableSearchRequest);
    return ResponseEntity.ok(userRepository.findAll(specs));
  }
}
