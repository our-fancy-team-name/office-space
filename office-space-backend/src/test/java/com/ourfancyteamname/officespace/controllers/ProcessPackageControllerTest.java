package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.ProcessPackageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProcessPackageControllerTest {

  @InjectMocks
  private ProcessPackageController controller;

  @Mock
  private ProcessPackageService processPackageService;

  @Test
  public void getValidPksToAdd() {
    controller.getValidPksToAdd(1);
    Mockito.verify(processPackageService, Mockito.times(1)).getValidPksToAdd(1);
  }

  @Test
  public void addPkgToCltNode() {
    controller.addPkgToCltNode(null);
    Mockito.verify(processPackageService, Mockito.times(1)).addPkgToCltNode(null);
  }
}
