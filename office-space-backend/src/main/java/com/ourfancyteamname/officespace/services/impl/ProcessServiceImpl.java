package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.dtos.GraphDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements ProcessService {

  @Autowired
  private ProcessClusterRepository clusterRepository;

  @Autowired
  private ClusterNodeRepository clusterNodeRepository;

  @Autowired
  private ClusterNodePathRepository pathRepository;

  @Autowired
  private ProcessGeneralConverter processGeneralConverter;

  @Override
  public GraphDto getGraph(Integer clusterId) {
    GraphDto result = new GraphDto();
    result.setCluster(clusterRepository.findById(clusterId)
        .map(processGeneralConverter::fromClusterToDto)
        .orElseThrow(errorNotFound()));

    result.setNodes(clusterNodeRepository.getClusterNodesByClusterId(clusterId));

    result.setClusterNodesPath(pathRepository.findAllByCLusterId(clusterId)
        .stream()
        .map(processGeneralConverter::fromPathToDto)
        .collect(Collectors.toList()));
    return result;
  }

  @Override
  public ClusterNode addNodeToCluster(GraphDto graphDto) {
    ClusterNode clusterNode = ClusterNode.builder()
        .clusterId(graphDto.getCluster().getId())
        .nodeId(graphDto.getNodes().get(0).getId())
        .build();
    return clusterNodeRepository.save(clusterNode);
  }

  private Supplier<IllegalArgumentException> errorNotFound() {
    return () -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name());
  }
}
