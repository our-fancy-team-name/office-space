package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.PackageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PackageControllerTest {

  @InjectMocks
  private PackageController controller;

  @Mock
  private PackageService packageService;

  @Test
  void create() {
    controller.create(null);
    Mockito.verify(packageService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  void getListView() {
    controller.getListView(null);
    Mockito.verify(packageService, Mockito.times(1)).getListView(Mockito.any());
  }

  @Test
  void update() {
    controller.update(null);
    Mockito.verify(packageService, Mockito.times(1)).update(Mockito.any());
  }

  @Test
  void delete() {
    controller.delete(null);
    Mockito.verify(packageService, Mockito.times(1)).delete(Mockito.any());
  }
}
