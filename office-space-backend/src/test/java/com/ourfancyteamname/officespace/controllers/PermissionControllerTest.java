package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PermissionControllerTest {

  @InjectMocks
  private PermissionController controller;

  @Mock
  private PermissionService permissionService;

  @Test
  public void getPermissionByRole() {
    controller.getPermissionByRole(null);
    Mockito.verify(permissionService, Mockito.times(1)).findAllPermissionByRole(Mockito.any());
  }
}
