package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.services.PermissionService;
import com.ourfancyteamname.officespace.services.RoleService;
import com.ourfancyteamname.officespace.services.UserService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    verifyInvoke1Time(roleService).updateRole(any());
    verifyInvoke1Time(roleService).updateRole(any());
    verifyInvoke1Time(userService).updateUserRole(any(), any());
    verifyInvoke1Time(permissionService).updateRolePermission(any(), any());
  }

  @Test
  void create() {
    controller.create(RoleUserUpdateDto.builder().build());
    verifyInvoke1Time(roleService).createRole(any());
    verifyInvoke1Time(userService).createUserRole(any(), any());
    verifyInvoke1Time(permissionService).createRolePermission(any(), any());
  }

  @Test
  void delete() {
    controller.delete(null);
    verifyInvoke1Time(roleService).deleteRole(any());
    verifyInvoke1Time(userService).deleteUserRoleByRoleId(any());
    verifyInvoke1Time(permissionService).deleteRolePermissionByRoleId(any());
  }

  @Test
  void getRoleUserListView() {
    controller.getRoleUserListView(null);
    verifyInvoke1Time(roleService).getRoleUserListView(any());
  }

  @Test
  void getAllRoleCode() {
    controller.getAllRoleCode();
    verifyInvoke1Time(roleService).getRoleCodes();
  }
}
