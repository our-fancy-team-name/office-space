package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.ProcessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProcessControllerTest {

  @InjectMocks
  private ProcessController controller;

  @Mock
  private ProcessService processService;

  @Test
  void getByClusterId() {
    controller.getByClusterId(null);
    Mockito.verify(processService, Mockito.times(1)).getGraph(Mockito.any());
  }

  @Test
  void addNodeToCluster() {
    controller.addNodeToCluster(null);
    Mockito.verify(processService, Mockito.times(1)).addNodeToCluster(Mockito.any());
  }

  @Test
  void removeNodeFromCluster() {
    controller.removeNodeFromCluster(null);
    Mockito.verify(processService, Mockito.times(1)).removeNodeFromCluster(Mockito.any());
  }

  @Test
  void editClusterNode() {
    controller.editClusterNode(null);
    Mockito.verify(processService, Mockito.times(1)).editClusterNode(Mockito.any());
  }

  @Test
  void addPath() {
    controller.addPath(null, null);
    Mockito.verify(processService, Mockito.times(1)).addSinglePath(Mockito.any(), Mockito.any());
  }

  @Test
  void removePath() {
    controller.removePath(null);
    Mockito.verify(processService, Mockito.times(1)).removePath(Mockito.any());
  }

}
