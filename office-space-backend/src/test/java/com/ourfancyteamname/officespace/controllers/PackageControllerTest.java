package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.PackageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PackageControllerTest {

  @InjectMocks
  private PackageController controller;

  @Mock
  private PackageService packageService;

  @Test
  public void create() {
    controller.create(null);
    Mockito.verify(packageService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  public void getListView() {
    controller.getListView(null);
    Mockito.verify(packageService, Mockito.times(1)).getListView(Mockito.any());
  }

  @Test
  public void update() {
    controller.update(null);
    Mockito.verify(packageService, Mockito.times(1)).update(Mockito.any());
  }

  @Test
  public void delete() {
    controller.delete(null);
    Mockito.verify(packageService, Mockito.times(1)).delete(Mockito.any());
  }
}
