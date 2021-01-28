package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.services.PackageService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PackageControllerTest {

  @InjectMocks
  private PackageController controller;

  @Mock
  private PackageService packageService;

  @Test
  void create() {
    controller.create(null);
    verifyInvoke1Time(packageService).create(any());
  }

  @Test
  void getListView() {
    controller.getListView(null);
    verifyInvoke1Time(packageService).getListView(any());
  }

  @Test
  void update() {
    controller.update(null);
    verifyInvoke1Time(packageService).update(any());
  }

  @Test
  void delete() {
    controller.delete(null);
    verifyInvoke1Time(packageService).delete(any());
  }
}
