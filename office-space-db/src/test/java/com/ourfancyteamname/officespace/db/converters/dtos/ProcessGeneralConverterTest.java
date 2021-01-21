package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProcessGeneralConverterTest {

  private final ProcessGeneralConverter converter = Mappers.getMapper(ProcessGeneralConverter.class);

  @Test
  void fromClusterToDto() {
    Assertions.assertNull(converter.fromClusterToDto(null));
    Assertions.assertNotNull(converter.fromClusterToDto(new ProcessCluster()));
  }

  @Test
  void fromNodeToDto() {
    Assertions.assertNull(converter.fromNodeToDto(null));
    Assertions.assertNotNull(converter.fromNodeToDto(new ProcessNode()));
  }

  @Test
  void fromPathToDto() {
    Assertions.assertNull(converter.fromPathToDto(null));
    Assertions.assertNotNull(converter.fromPathToDto(new ClusterNodePath()));
  }

  @Test
  void fromClusterNodeEditToPath() {
    Assertions.assertNull(converter.fromClusterNodeEditToPath(null));
    Assertions.assertNotNull(converter.fromClusterNodeEditToPath(new ProcessGeneralDto()));
  }

  @Test
  void fromDtoToClusterEntity() {
    Assertions.assertNull(converter.fromDtoToClusterEntity(null));
    Assertions.assertNotNull(converter.fromDtoToClusterEntity(new ProcessGeneralDto()));
  }

  @Test
  void fromDtoToNodeEntity() {
    Assertions.assertNull(converter.fromDtoToNodeEntity(null));
    Assertions.assertNotNull(converter.fromDtoToNodeEntity(new ProcessGeneralDto()));
  }
}
