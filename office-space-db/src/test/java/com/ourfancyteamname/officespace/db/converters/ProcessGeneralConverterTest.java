package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessGeneralConverterTest {

  private ProcessGeneralConverter converter = Mappers.getMapper(ProcessGeneralConverter.class);

  @Test
  public void fromClusterToDto() {
    Assert.assertNull(converter.fromClusterToDto(null));
    Assert.assertNotNull(converter.fromClusterToDto(new ProcessCluster()));
  }

  @Test
  public void fromNodeToDto() {
    Assert.assertNull(converter.fromNodeToDto(null));
    Assert.assertNotNull(converter.fromNodeToDto(new ProcessNode()));
  }

  @Test
  public void fromPathToDto() {
    Assert.assertNull(converter.fromPathToDto(null));
    Assert.assertNotNull(converter.fromPathToDto(new ClusterNodePath()));
  }

  @Test
  public void fromClusterNodeEditToPath() {
    Assert.assertNull(converter.fromClusterNodeEditToPath(null));
    Assert.assertNotNull(converter.fromClusterNodeEditToPath(new ProcessGeneralDto()));
  }

  @Test
  public void fromDtoToClusterEntity() {
    Assert.assertNull(converter.fromDtoToClusterEntity(null));
    Assert.assertNotNull(converter.fromDtoToClusterEntity(new ProcessGeneralDto()));
  }

  @Test
  public void fromDtoToNodeEntity() {
    Assert.assertNull(converter.fromDtoToNodeEntity(null));
    Assert.assertNotNull(converter.fromDtoToNodeEntity(new ProcessGeneralDto()));
  }
}
