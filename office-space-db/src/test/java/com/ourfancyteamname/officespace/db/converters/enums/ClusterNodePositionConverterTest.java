package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClusterNodePositionConverterTest {


  @InjectMocks
  private ClusterNodePositionConverter clusterNodePositionConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(ClusterNodePosition.BODY);
    Assertions.assertEquals(gender, ClusterNodePosition.BODY.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(null);
    Assertions.assertEquals(null, gender);
  }

  @Test
  public void convertToEntityAttribute_success() {
    ClusterNodePosition clusterNodePosition =
        clusterNodePositionConverter.convertToEntityAttribute(ClusterNodePosition.HEAD.name());
    Assertions.assertEquals(ClusterNodePosition.HEAD, clusterNodePosition);
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void convertToEntityAttribute_errorInvalid() {
//    clusterNodePositionConverter.convertToEntityAttribute("HI");
//  }

  @Test
  public void convertToEntityAttribute_errorNull() {
    ClusterNodePosition clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute(null);
    Assertions.assertEquals(null, clusterNodePosition);
  }
}
