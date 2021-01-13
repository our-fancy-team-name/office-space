package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePackageRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements ProcessService {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private ProcessClusterRepository clusterRepository;

  @Autowired
  private ClusterNodeRepository clusterNodeRepository;

  @Autowired
  private ClusterNodePathRepository pathRepository;

  @Autowired
  private ProcessGeneralConverter processGeneralConverter;

  @Autowired
  private ClusterNodePackageRepository clusterNodePackageRepository;

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
    if (clusterNodeRepository
        .existsByClusterIdAndNodeId(graphDto.getCluster().getId(), graphDto.getNodes().get(0).getId())) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    ClusterNode clusterNode = ClusterNode.builder()
        .clusterId(graphDto.getCluster().getId())
        .nodeId(graphDto.getNodes().get(0).getId())
        .build();
    return clusterNodeRepository.save(clusterNode);
  }

  @Override
  public void addSinglePath(Integer clusterIdFrom, Integer clusterIdTo) {
    pathRepository.save(ClusterNodePath.builder()
        .clusterNodeIdFrom(clusterIdFrom)
        .clusterNodeIdTo(clusterIdTo)
        .build());
  }

  @Override
  public void removePath(Integer pathId) {
    pathRepository.deleteById(pathId);
  }

  @Override
  public void removeNodeFromCluster(Integer clusterNodeId) {
    ClusterNode clusterNode = clusterNodeRepository.findById(clusterNodeId)
        .orElseThrow(errorNotFound());
    pathRepository.removeByClusterNodeIdToOrClusterNodeIdFrom(clusterNode.getId(), clusterNode.getId());
    entityManager.flush();
    clusterNodePackageRepository.removeByClusterNodeId(clusterNodeId);
    entityManager.flush();
    clusterNodeRepository.delete(clusterNode);
  }

  @Override
  public void editClusterNode(ClusterNodeEditDto clusterNodeEditDto) {
    editOrCreateInputPath(clusterNodeEditDto);
    editOrCreateOutputPath(clusterNodeEditDto);
  }

  private void editOrCreateOutputPath(ClusterNodeEditDto clusterNodeEditDto) {
    List<ClusterNodePath> outputPaths = pathRepository.findByClusterNodeIdFrom(clusterNodeEditDto.getId());
    if (CollectionUtils.isEmpty(clusterNodeEditDto.getOutput())) {
      pathRepository.deleteAll(outputPaths);
    } else {
      pathRepository.saveAll(clusterNodeEditDto.getOutput().stream()
          .map(dto -> outputPaths.stream()
              .filter(e -> e.getClusterNodeIdTo().equals(dto.getClusterNodeIdTo()))
              .findAny()
              .map(e -> {
                e.setLabel(dto.getLabel());
                e.setDescription(dto.getDescription());
                return e;
              })
              .orElseGet(() -> processGeneralConverter.fromClusterNodeEditToPath(dto)))
          .collect(Collectors.toList()));
    }
  }

  private void editOrCreateInputPath(ClusterNodeEditDto clusterNodeEditDto) {
    List<ClusterNodePath> inputPaths = pathRepository.findByClusterNodeIdTo(clusterNodeEditDto.getId());
    if (CollectionUtils.isEmpty(clusterNodeEditDto.getInput())) {
      pathRepository.deleteAll(inputPaths);
    } else {
      pathRepository.saveAll(clusterNodeEditDto.getInput().stream()
          .map(dto -> inputPaths.stream()
              .filter(e -> e.getClusterNodeIdFrom().equals(dto.getClusterNodeIdFrom()))
              .findAny()
              .map(e -> {
                e.setLabel(dto.getLabel());
                e.setDescription(dto.getDescription());
                return e;
              })
              .orElseGet(() -> processGeneralConverter.fromClusterNodeEditToPath(dto)))
          .collect(Collectors.toList()));
    }
  }

  private Supplier<IllegalArgumentException> errorNotFound() {
    return () -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name());
  }
}
