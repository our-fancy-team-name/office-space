package com.ourfancyteamname.officespace.db.entities.view;

import org.junit.Assert;
import org.junit.Test;

public class ProcessListViewTest {

  @Test
  public void processListViewTest() {
    Integer packageId = 1;
    String serial = "serial";
    ProcessListView processListView = new ProcessListView(serial, packageId);
    Assert.assertEquals(serial, processListView.getSerial());
    Assert.assertEquals(packageId, processListView.getPackageId());
  }
}
