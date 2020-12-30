package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessGeneralConverter {

  ProcessGeneralDto fromClusterToDto(ProcessCluster processCluster);

  ProcessGeneralDto fromNodeToDto(ProcessNode processNode);

  ProcessGeneralDto fromPathToDto(ClusterNodePath clusterNodePath);

  ClusterNodePath fromClusterNodeEditToPath(ProcessGeneralDto clusterNodeEditDto);

  ProcessCluster fromDtoToClusterEntity(ProcessGeneralDto processGeneralDto);

  ProcessNode fromDtoToNodeEntity(ProcessGeneralDto processGeneralDto);
}
