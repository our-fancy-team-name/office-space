package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.services.ClusterService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ClusterControllerTest {

  @InjectMocks
  private ClusterController controller;

  @Mock
  private ClusterService clusterService;

  @Test
  void create() {
    controller.create(ProcessGeneralDto.builder().build());
    verifyInvoke1Time(clusterService).create(Mockito.any());
  }

  @Test
  void getListView() {
    controller.getListView(null);
    verifyInvoke1Time(clusterService).getListView(Mockito.any());
  }

  @Test
  void update() {
    controller.update(null);
    verifyInvoke1Time(clusterService).update(Mockito.any());
  }
}
