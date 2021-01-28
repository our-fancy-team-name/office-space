package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.services.UserService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class UserControllerTest {

  @InjectMocks
  private UserController controller;

  @Mock
  private UserService userService;

  @Test
  void getAll() {
    controller.getAll(null);
    verifyInvoke1Time(userService).findAllByPaging(any());
  }

  @Test
  void update() {
    controller.update(RoleUserUpdateDto.builder().build());
    verifyInvoke1Time(userService).editUser(any());
    verifyInvoke1Time(userService).updateRoleUser(any(), any());
  }

  @Test
  void getUserRoleListView() {
    controller.getUserRoleListView(null);
    verifyInvoke1Time(userService).findUserRoleListView(any());
  }

  @Test
  void getUserDetails() {
    controller.getUserDetails(null);
    verifyInvoke1Time(userService).findById(any());
  }

  @Test
  void deleteUser() {
    controller.deleteUser(null);
    verifyInvoke1Time(userService).removeUser(any());
  }

  @Test
  void createUser() {
    controller.createUser(RoleUserUpdateDto.builder().build());
    verifyInvoke1Time(userService).createUser(any());
    verifyInvoke1Time(userService).createRoleUser(any(), any());
  }
}
