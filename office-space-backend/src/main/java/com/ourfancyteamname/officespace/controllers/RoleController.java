package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.annotations.CanEditRole;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.PermissionService;
import com.ourfancyteamname.officespace.services.RoleService;
import com.ourfancyteamname.officespace.services.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  @Autowired
  private PermissionService permissionService;

  @PostMapping
  @Transactional
  @CanEditRole
  public ResponseEntity<Void> update(@RequestBody RoleUserUpdateDto roleUserUpdateDto) {
    roleService.updateRole(roleUserUpdateDto.getRoleDto());
    userService.updateUserRole(roleUserUpdateDto.getRoleDto(), roleUserUpdateDto.getUsers());
    permissionService.updateRolePermission(roleUserUpdateDto.getRoleDto(), roleUserUpdateDto.getPermissionDto());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/create")
  @Transactional
  @CanEditRole
  public ResponseEntity<Void> create(@RequestBody RoleUserUpdateDto roleUserUpdateDto) {
    Role role = roleService.createRole(roleUserUpdateDto.getRoleDto());
    userService.createUserRole(role, roleUserUpdateDto.getUsers());
    permissionService.createRolePermission(role, roleUserUpdateDto.getPermissionDto());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  @CanEditRole
  public ResponseEntity<Void> delete(@PathVariable("id") Integer roleId) {
    permissionService.deleteRolePermissionByRoleId(roleId);
    userService.deleteUserRoleByRoleId(roleId);
    roleService.deleteRole(roleId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list")
  public ResponseEntity<Page<RoleUserListView>> getRoleUserListView(
      @RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(roleService.getRoleUserListView(tableSearchRequest));
  }

  @GetMapping("/list-code")
  public ResponseEntity<List<String>> getAllRoleCode() {
    return ResponseEntity.ok(roleService.getRoleCodes());
  }
}
