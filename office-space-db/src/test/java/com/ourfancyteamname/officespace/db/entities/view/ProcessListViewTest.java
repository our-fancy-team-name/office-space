package com.ourfancyteamname.officespace.db.entities.view;

import org.junit.Assert;
import org.junit.Test;

public class ProcessListViewTest {

  @Test
  public void processListViewTest() {
    ProcessListView processListView = new ProcessListView("serial", 1);
    Assert.assertEquals("serial", processListView.getSerial());
    Assert.assertTrue(1 == processListView.getPackageId());
  }
}
