package com.ourfancyteamname.officespace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.annotations.CanEditUser;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<Page<UserDto>> getAll(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.findAllByPaging(tableSearchRequest));
  }

  @PostMapping("/update")
  @Transactional
  @CanEditUser
  public ResponseEntity<Void> update(@RequestBody RoleUserUpdateDto roleUserUpdateDto) {
    userService.editUser(roleUserUpdateDto.getUserDto());
    userService.updateRoleUser(roleUserUpdateDto.getUserDto(), roleUserUpdateDto.getRoles());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list-role")
  public ResponseEntity<Page<UserRoleListView>> getUserRoleListView(
      @RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.findUserRoleListView(tableSearchRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Integer userId) {
    return ResponseEntity.ok(userService.findById(userId));
  }

  @DeleteMapping("/{id}")
  @Transactional
  @CanEditUser
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer userId) {
    userService.removeUser(userId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/create")
  @Transactional
  @CanEditUser
  public ResponseEntity<Void> createUser(@RequestBody RoleUserUpdateDto roleUserUpdateDto) {
    final var user = userService.createUser(roleUserUpdateDto.getUserDto());
    userService.createRoleUser(user, roleUserUpdateDto.getRoles());
    return ResponseEntity.ok().build();
  }

}
