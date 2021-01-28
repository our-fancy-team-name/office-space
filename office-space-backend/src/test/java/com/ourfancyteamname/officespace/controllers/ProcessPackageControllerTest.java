package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.services.ProcessPackageService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessPackageControllerTest {

  @InjectMocks
  private ProcessPackageController controller;

  @Mock
  private ProcessPackageService processPackageService;

  @Test
  void getValidPksToAdd() {
    controller.getValidPksToAdd(1);
    verifyInvoke1Time(processPackageService).getValidPksToAdd(1);
  }

  @Test
  void addPkgToCltNode() {
    controller.addPkgToCltNode(null);
    verifyInvoke1Time(processPackageService).addPkgToCltNode(null);
  }
}
