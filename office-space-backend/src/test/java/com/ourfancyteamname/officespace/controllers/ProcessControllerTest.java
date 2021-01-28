package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ourfancyteamname.officespace.services.ProcessService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessControllerTest {

  @InjectMocks
  private ProcessController controller;

  @Mock
  private ProcessService processService;

  @Test
  void getByClusterId() {
    controller.getByClusterId(null);
    verifyInvoke1Time(processService).getGraph(any());
  }

  @Test
  void addNodeToCluster() {
    controller.addNodeToCluster(null);
    verifyInvoke1Time(processService).addNodeToCluster(any());
  }

  @Test
  void removeNodeFromCluster() {
    controller.removeNodeFromCluster(null);
    verifyInvoke1Time(processService).removeNodeFromCluster(any());
  }

  @Test
  void editClusterNode() {
    controller.editClusterNode(null);
    verifyInvoke1Time(processService).editClusterNode(any());
  }

  @Test
  void addPath() {
    controller.addPath(null, null);
    verifyInvoke1Time(processService).addSinglePath(Mockito.any(), Mockito.any());
  }

  @Test
  void removePath() {
    controller.removePath(null);
    verifyInvoke1Time(processService).removePath(Mockito.any());
  }

}
