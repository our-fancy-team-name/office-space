package com.ourfancyteamname.officespace.db.entities.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessListViewTest {

  @Test
  void processListViewTest() {
    Integer packageId = 1;
    String serial = "serial";
    ProcessListView processListView = new ProcessListView(serial, packageId);
    Assertions.assertEquals(serial, processListView.getSerial());
    Assertions.assertEquals(packageId, processListView.getPackageId());
  }
}
