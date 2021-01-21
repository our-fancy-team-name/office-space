package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.RoleUserUpdateDto;
import com.ourfancyteamname.officespace.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks
  private UserController controller;

  @Mock
  private UserService userService;

  @Test
  void getAll() {
    controller.getAll(null);
    Mockito.verify(userService, Mockito.times(1)).findAllByPaging(Mockito.any());
  }

  @Test
  void update() {
    controller.update(RoleUserUpdateDto.builder().build());
    Mockito.verify(userService, Mockito.times(1)).editUser(Mockito.any());
    Mockito.verify(userService, Mockito.times(1)).updateRoleUser(Mockito.any(), Mockito.any());
  }

  @Test
  void getUserRoleListView() {
    controller.getUserRoleListView(null);
    Mockito.verify(userService, Mockito.times(1)).findUserRoleListView(Mockito.any());
  }

  @Test
  void getUserDetails() {
    controller.getUserDetails(null);
    Mockito.verify(userService, Mockito.times(1)).findById(Mockito.any());
  }

  @Test
  void deleteUser() {
    controller.deleteUser(null);
    Mockito.verify(userService, Mockito.times(1)).removeUser(Mockito.any());
  }

  @Test
  void createUser() {
    controller.createUser(RoleUserUpdateDto.builder().build());
    Mockito.verify(userService, Mockito.times(1)).createUser(Mockito.any());
    Mockito.verify(userService, Mockito.times(1)).createRoleUser(Mockito.any(), Mockito.any());
  }
}
