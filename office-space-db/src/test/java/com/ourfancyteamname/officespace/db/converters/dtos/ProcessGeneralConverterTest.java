package com.ourfancyteamname.officespace.db.converters.dtos;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessGeneralConverterTest {

  private final ProcessGeneralConverter converter = Mappers.getMapper(ProcessGeneralConverter.class);

  @Test
  void fromClusterToDto() {
    assertNull(converter.fromClusterToDto(null));
    assertNotNull(converter.fromClusterToDto(new ProcessCluster()));
  }

  @Test
  void fromNodeToDto() {
    assertNull(converter.fromNodeToDto(null));
    assertNotNull(converter.fromNodeToDto(new ProcessNode()));
  }

  @Test
  void fromPathToDto() {
    assertNull(converter.fromPathToDto(null));
    assertNotNull(converter.fromPathToDto(new ClusterNodePath()));
  }

  @Test
  void fromClusterNodeEditToPath() {
    assertNull(converter.fromClusterNodeEditToPath(null));
    assertNotNull(converter.fromClusterNodeEditToPath(new ProcessGeneralDto()));
  }

  @Test
  void fromDtoToClusterEntity() {
    assertNull(converter.fromDtoToClusterEntity(null));
    assertNotNull(converter.fromDtoToClusterEntity(new ProcessGeneralDto()));
  }

  @Test
  void fromDtoToNodeEntity() {
    assertNull(converter.fromDtoToNodeEntity(null));
    assertNotNull(converter.fromDtoToNodeEntity(new ProcessGeneralDto()));
  }
}
