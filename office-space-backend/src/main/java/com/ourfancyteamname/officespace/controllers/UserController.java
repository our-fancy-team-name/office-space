package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<Page<UserDto>> getAll(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.findAllByPaging(tableSearchRequest));
  }

  @PostMapping("/list-role")
  public ResponseEntity<Page<UserRoleListView>> getUserRoleListView(
      @RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.findUserRoleListView(tableSearchRequest));
  }

  @PostMapping("/{id}")
  public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Integer userId) {
    return ResponseEntity.ok(userService.findById(userId));
  }

}
