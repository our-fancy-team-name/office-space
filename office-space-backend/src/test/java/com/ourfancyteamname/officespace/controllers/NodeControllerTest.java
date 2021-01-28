package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.services.NodeService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class NodeControllerTest {

  @InjectMocks
  private NodeController controller;

  @Mock
  private NodeService nodeService;

  @Test
  void create() {
    controller.create(null);
    verifyInvoke1Time(nodeService).create(any());
  }

  @Test
  void getListView() {
    controller.getListView(null);
    verifyInvoke1Time(nodeService).getListView(any());
  }

  @Test
  void update() {
    controller.update(null);
    verifyInvoke1Time(nodeService).update(any());
  }
}
