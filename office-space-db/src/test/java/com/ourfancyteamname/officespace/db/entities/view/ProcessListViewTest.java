package com.ourfancyteamname.officespace.db.entities.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessListViewTest {

  @Test
  void processListViewTest() {
    Integer packageId = 1;
    String serial = "serial";
    ProcessListView processListView = new ProcessListView(serial, packageId);
    assertEquals(serial, processListView.getSerial());
    assertEquals(packageId, processListView.getPackageId());
  }
}
