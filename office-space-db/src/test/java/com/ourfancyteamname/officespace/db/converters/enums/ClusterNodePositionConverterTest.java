package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ClusterNodePositionConverterTest {


  @InjectMocks
  private ClusterNodePositionConverter clusterNodePositionConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(ClusterNodePosition.BODY);
    Assert.assertEquals(gender, ClusterNodePosition.BODY.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String gender = clusterNodePositionConverter.convertToDatabaseColumn(null);
    Assert.assertEquals(null, gender);
  }

  @Test
  public void convertToEntityAttribute_success() {
    ClusterNodePosition clusterNodePosition =
        clusterNodePositionConverter.convertToEntityAttribute(ClusterNodePosition.HEAD.name());
    Assert.assertEquals(ClusterNodePosition.HEAD, clusterNodePosition);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorInvalid() {
    ClusterNodePosition clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute("HI");
    Assert.assertEquals(ClusterNodePosition.TAIL, clusterNodePosition);
  }

  @Test
  public void convertToEntityAttribute_errorNull() {
    ClusterNodePosition clusterNodePosition = clusterNodePositionConverter.convertToEntityAttribute(null);
    Assert.assertEquals(null, clusterNodePosition);
  }
}
