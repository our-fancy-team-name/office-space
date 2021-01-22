package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.services.PermissionService;
import com.ourfancyteamname.officespace.services.RoleService;
import com.ourfancyteamname.officespace.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

  @InjectMocks
  private RoleController controller;

  @Mock
  private RoleService roleService;

  @Mock
  private UserService userService;

  @Mock
  private PermissionService permissionService;

  @Test
  void update() {
    controller.update(RoleUserUpdateDto.builder().build());
    Mockito.verify(roleService, Mockito.times(1)).updateRole(Mockito.any());
    Mockito.verify(userService, Mockito.times(1)).updateUserRole(Mockito.any(), Mockito.any());
    Mockito.verify(permissionService, Mockito.times(1)).updateRolePermission(Mockito.any(), Mockito.any());
  }

  @Test
  void create() {
    controller.create(RoleUserUpdateDto.builder().build());
    Mockito.verify(roleService, Mockito.times(1)).createRole(Mockito.any());
    Mockito.verify(userService, Mockito.times(1)).createUserRole(Mockito.any(), Mockito.any());
    Mockito.verify(permissionService, Mockito.times(1)).createRolePermission(Mockito.any(), Mockito.any());
  }

  @Test
  void delete() {
    controller.delete(null);
    Mockito.verify(roleService, Mockito.times(1)).deleteRole(Mockito.any());
    Mockito.verify(userService, Mockito.times(1)).deleteUserRoleByRoleId(Mockito.any());
    Mockito.verify(permissionService, Mockito.times(1)).deleteRolePermissionByRoleId(Mockito.any());
  }

  @Test
  void getRoleUserListView() {
    controller.getRoleUserListView(null);
    Mockito.verify(roleService, Mockito.times(1)).getRoleUserListView(Mockito.any());
  }

  @Test
  void getAllRoleCode() {
    controller.getAllRoleCode();
    Mockito.verify(roleService, Mockito.times(1)).getRoleCodes();
  }
}
