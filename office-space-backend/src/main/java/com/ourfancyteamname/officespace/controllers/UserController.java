package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.db.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private UserService userService;

  @PostMapping
  public ResponseEntity<Page<UserDto>> getAll(@RequestBody @Valid TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.findAllByPaging(tableSearchRequest));
  }

  @GetMapping("/{role}")
  public ResponseEntity<List<PermissionDto>> getPermissionByRole(@PathVariable("role") String role) {
    return ResponseEntity.ok(userService.findAllPermissionByRole(role));
  }

  @PostMapping("/role-list")
  public ResponseEntity<Page<RoleUserListView>> getRoleUserListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(userService.getRolUserListView(tableSearchRequest));
  }
}
