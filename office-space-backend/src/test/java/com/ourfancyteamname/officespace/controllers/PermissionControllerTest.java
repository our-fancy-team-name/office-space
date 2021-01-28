package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.services.PermissionService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PermissionControllerTest {

  @InjectMocks
  private PermissionController controller;

  @Mock
  private PermissionService permissionService;

  @Test
  void getPermissionByRole() {
    controller.getPermissionByRole(null);
    verifyInvoke1Time(permissionService).findAllPermissionByRole(any());
  }
}
