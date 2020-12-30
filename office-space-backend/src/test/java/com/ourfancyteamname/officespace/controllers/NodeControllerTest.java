package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.NodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NodeControllerTest {

  @InjectMocks
  private NodeController controller;

  @Mock
  private NodeService nodeService;

  @Test
  public void create() {
    controller.create(null);
    Mockito.verify(nodeService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  public void getListView() {
    controller.getListView(null);
    Mockito.verify(nodeService, Mockito.times(1)).getListView(Mockito.any());
  }

  @Test
  public void update() {
    controller.update(null);
    Mockito.verify(nodeService, Mockito.times(1)).update(Mockito.any());
  }
}
