package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.services.ClusterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClusterControllerTest {

  @InjectMocks
  private ClusterController controller;

  @Mock
  private ClusterService clusterService;

  @Test
  public void create() {
    controller.create(ProcessGeneralDto.builder().build());
    Mockito.verify(clusterService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  public void getListView() {
    controller.getListView(null);
    Mockito.verify(clusterService, Mockito.times(1)).getListView(Mockito.any());
  }

  @Test
  public void update() {
    controller.update(null);
    Mockito.verify(clusterService, Mockito.times(1)).update(Mockito.any());
  }
}
