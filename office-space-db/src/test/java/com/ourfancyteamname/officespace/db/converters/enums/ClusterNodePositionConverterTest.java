package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClusterNodePositionConverterTest {

  @InjectMocks
  private ClusterNodePositionConverter clusterNodePositionConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(ClusterNodePosition.BODY);
    Assertions.assertEquals(gender, ClusterNodePosition.BODY.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(null);
    Assertions.assertNull(gender);
  }

  @Test
  void convertToEntityAttribute_success() {
    ClusterNodePosition clusterNodePosition =
        clusterNodePositionConverter.convertToEntityAttribute(ClusterNodePosition.HEAD.name());
    Assertions.assertEquals(ClusterNodePosition.HEAD, clusterNodePosition);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> clusterNodePositionConverter.convertToEntityAttribute("HI")
    );
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    ClusterNodePosition clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute(null);
    Assertions.assertNull(clusterNodePosition);
  }
}
