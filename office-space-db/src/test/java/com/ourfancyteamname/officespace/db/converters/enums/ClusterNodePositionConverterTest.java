package com.ourfancyteamname.officespace.db.converters.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;
import com.ourfancyteamname.officespace.test.services.AssertionHelper;

@UnitTest
class ClusterNodePositionConverterTest {

  @InjectMocks
  private ClusterNodePositionConverter clusterNodePositionConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(ClusterNodePosition.BODY);
    assertEquals(gender, ClusterNodePosition.BODY.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(null);
    assertNull(gender);
  }

  @Test
  void convertToEntityAttribute_success() {
    var clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute(ClusterNodePosition.HEAD.name());
    assertEquals(ClusterNodePosition.HEAD, clusterNodePosition);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    AssertionHelper.assertThrowIllegal(() -> clusterNodePositionConverter.convertToEntityAttribute("HI"));
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    var clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute(null);
    assertNull(clusterNodePosition);
  }
}
