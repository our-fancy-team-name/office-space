package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalDuplicated;
import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalNotFound;
import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePackageRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessServiceImplTest {

  @InjectMocks
  private ProcessServiceImpl service;

  @Mock
  private EntityManager entityManager;

  @Mock
  private ProcessClusterRepository clusterRepository;

  @Mock
  private ClusterNodeRepository clusterNodeRepository;

  @Mock
  private ClusterNodePathRepository pathRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock
  private ClusterNodePackageRepository clusterNodePackageRepository;

  @Test
  void getGraph_notFound() {
    Integer clusterId = 1;
    mockReturn(clusterRepository.findById(clusterId), Optional.empty());
    assertThrowIllegalNotFound(() -> service.getGraph(clusterId));
  }

  @Test
  void getGraph_success() {
    Integer clusterId = 1;
    ProcessCluster cluster = ProcessCluster.builder().id(clusterId).build();
    ProcessGeneralDto clusterDto = ProcessGeneralDto.builder().id(clusterId).build();
    mockReturn(clusterRepository.findById(clusterId), Optional.of(cluster));
    mockReturn(processGeneralConverter.fromClusterToDto(cluster), clusterDto);
    mockReturn(pathRepository.findAllByCLusterId(clusterId),
        Collections.singletonList(ClusterNodePath.builder().build()));
    service.getGraph(clusterId);
    verifyInvoke1Time(processGeneralConverter).fromClusterToDto(cluster);
    verifyInvoke1Time(clusterRepository).findById(clusterId);
    verifyInvoke1Time(clusterNodeRepository).getClusterNodesByClusterId(clusterId);
    verifyInvoke1Time(pathRepository).findAllByCLusterId(clusterId);
    verifyInvoke1Time(processGeneralConverter).fromPathToDto(any());
  }

  @Test
  void addNodeToCluster_duplicated() {
    int clusterId = 1;
    int nodeId = 1;
    ProcessGeneralDto cluster = ProcessGeneralDto.builder().id(clusterId).build();
    ProcessGeneralDto node = ProcessGeneralDto.builder().id(nodeId).build();
    GraphDto graphDto = GraphDto.builder().cluster(cluster).nodes(Collections.singletonList(node)).build();
    mockReturn(clusterNodeRepository.existsByClusterIdAndNodeId(clusterId, nodeId), true);
    assertThrowIllegalDuplicated(() -> service.addNodeToCluster(graphDto));
  }

  @Test
  void addNodeToCluster_success() {
    int clusterId = 1;
    int nodeId = 1;
    ProcessGeneralDto cluster = ProcessGeneralDto.builder().id(clusterId).build();
    ProcessGeneralDto node = ProcessGeneralDto.builder().id(nodeId).build();
    GraphDto graphDto = GraphDto.builder().cluster(cluster).nodes(Collections.singletonList(node)).build();
    mockReturn(clusterNodeRepository.existsByClusterIdAndNodeId(clusterId, nodeId), false);
    service.addNodeToCluster(graphDto);
    verifyInvoke1Time(clusterNodeRepository).save(any(ClusterNode.class));
  }

  @Test
  void addSinglePath_success() {
    int from = 1;
    int to = 2;
    service.addSinglePath(from, to);
    verify(pathRepository, times(1)).save(any(ClusterNodePath.class));
  }

  @Test
  void removePath_success() {
    int pathId = 1;
    service.removePath(pathId);
    verify(pathRepository, times(1)).deleteById(pathId);
  }

  @Test
  void removeNodeFromCluster_notFound() {
    int clusterNodeId = 1;
    when(clusterNodeRepository.findById(clusterNodeId)).thenReturn(Optional.empty());
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.removeNodeFromCluster(clusterNodeId),
        ErrorCode.NOT_FOUND.name()
    );
  }

  @Test
  void removeNodeFromCluster_success() {
    int clusterNodeId = 1;
    ClusterNode clusterNode = ClusterNode.builder().id(clusterNodeId).build();
    when(clusterNodeRepository.findById(clusterNodeId)).thenReturn(Optional.of(clusterNode));
    service.removeNodeFromCluster(clusterNodeId);
    verify(clusterNodeRepository, times(1)).findById(clusterNodeId);
    verify(pathRepository, times(1))
        .removeByClusterNodeIdToOrClusterNodeIdFrom(clusterNodeId, clusterNodeId);
    verify(entityManager, times(2)).flush();
    verify(clusterNodePackageRepository, times(1)).removeByClusterNodeId(clusterNodeId);
    verify(clusterNodeRepository, times(1)).delete(clusterNode);
  }

  @Test
  void editClusterNode_emptyInputEmptyOutput() {
    int clusterNodeId = 1;
    List<ProcessGeneralDto> inputs = Collections.emptyList();
    List<ProcessGeneralDto> outputs = Collections.emptyList();
    ClusterNodeEditDto editDto = ClusterNodeEditDto.builder().id(clusterNodeId).input(inputs).output(outputs).build();
    when(pathRepository.findByClusterNodeIdFrom(clusterNodeId)).thenReturn(Collections.emptyList());
    when(pathRepository.findByClusterNodeIdTo(clusterNodeId)).thenReturn(Collections.emptyList());
    service.editClusterNode(editDto);
    verify(pathRepository, times(1)).findByClusterNodeIdFrom(clusterNodeId);
    verify(pathRepository, times(1)).findByClusterNodeIdTo(clusterNodeId);
    verify(pathRepository, times(2)).deleteAll(Collections.emptyList());
  }

  @Test
  void editClusterNode_emptyInput() {
    int clusterNodeId = 1;
    List<ProcessGeneralDto> inputs = Collections.emptyList();
    List<ProcessGeneralDto> outputs = Collections.singletonList(ProcessGeneralDto.builder().build());
    ClusterNodeEditDto editDto = ClusterNodeEditDto.builder().id(clusterNodeId).input(inputs).output(outputs).build();
    when(pathRepository.findByClusterNodeIdFrom(clusterNodeId)).thenReturn(Collections.emptyList());
    when(pathRepository.findByClusterNodeIdTo(clusterNodeId)).thenReturn(Collections.emptyList());
    when(processGeneralConverter.fromClusterNodeEditToPath(outputs.get(0)))
        .thenReturn(ClusterNodePath.builder().build());
    service.editClusterNode(editDto);
    verify(pathRepository, times(1)).findByClusterNodeIdFrom(clusterNodeId);
    verify(pathRepository, times(1)).findByClusterNodeIdTo(clusterNodeId);
    verify(pathRepository, times(1)).deleteAll(Collections.emptyList());
    verify(processGeneralConverter, times(1)).fromClusterNodeEditToPath(any());
    verify(pathRepository, times(1)).saveAll(any());
  }

  @Test
  void editClusterNode_emptyInput2() {
    int clusterNodeId = 1;
    int clusterIdNodeTo = 2;
    List<ProcessGeneralDto> inputs = Collections.emptyList();
    List<ProcessGeneralDto> outputs =
        Collections.singletonList(ProcessGeneralDto.builder().clusterNodeIdTo(clusterIdNodeTo).build());
    List<ClusterNodePath> clusterNodePathsFrom =
        Collections.singletonList(ClusterNodePath.builder().clusterNodeIdTo(clusterIdNodeTo).build());
    ClusterNodeEditDto editDto = ClusterNodeEditDto.builder().id(clusterNodeId).input(inputs).output(outputs).build();
    when(pathRepository.findByClusterNodeIdFrom(clusterNodeId)).thenReturn(clusterNodePathsFrom);
    when(pathRepository.findByClusterNodeIdTo(clusterNodeId)).thenReturn(Collections.emptyList());
    service.editClusterNode(editDto);
    verify(pathRepository, times(1)).findByClusterNodeIdFrom(clusterNodeId);
    verify(pathRepository, times(1)).findByClusterNodeIdTo(clusterNodeId);
    verify(pathRepository, times(1)).deleteAll(Collections.emptyList());
    verify(pathRepository, times(1)).saveAll(any());
  }

  @Test
  void editClusterNode_emptyOutput() {
    int clusterNodeId = 1;
    List<ProcessGeneralDto> inputs = Collections.singletonList(ProcessGeneralDto.builder().build());
    List<ProcessGeneralDto> outputs = Collections.emptyList();
    ClusterNodeEditDto editDto = ClusterNodeEditDto.builder().id(clusterNodeId).input(inputs).output(outputs).build();
    when(pathRepository.findByClusterNodeIdFrom(clusterNodeId)).thenReturn(Collections.emptyList());
    when(pathRepository.findByClusterNodeIdTo(clusterNodeId)).thenReturn(Collections.emptyList());
    when(processGeneralConverter.fromClusterNodeEditToPath(inputs.get(0)))
        .thenReturn(ClusterNodePath.builder().build());
    service.editClusterNode(editDto);
    verify(pathRepository, times(1)).findByClusterNodeIdFrom(clusterNodeId);
    verify(pathRepository, times(1)).findByClusterNodeIdTo(clusterNodeId);
    verify(pathRepository, times(1)).deleteAll(Collections.emptyList());
    verify(processGeneralConverter, times(1)).fromClusterNodeEditToPath(any());
    verify(pathRepository, times(1)).saveAll(any());
  }

  @Test
  void editClusterNode_emptyOutput2() {
    int clusterNodeId = 1;
    int clusterIdNodeFrom = 3;
    List<ProcessGeneralDto> inputs =
        Collections.singletonList(ProcessGeneralDto.builder().clusterNodeIdFrom(clusterIdNodeFrom).build());
    List<ProcessGeneralDto> outputs = Collections.emptyList();
    List<ClusterNodePath> clusterNodePathsFrom =
        Collections.singletonList(ClusterNodePath.builder().clusterNodeIdFrom(clusterIdNodeFrom).build());
    ClusterNodeEditDto editDto = ClusterNodeEditDto.builder().id(clusterNodeId).input(inputs).output(outputs).build();
    when(pathRepository.findByClusterNodeIdFrom(clusterNodeId)).thenReturn(Collections.emptyList());
    when(pathRepository.findByClusterNodeIdTo(clusterNodeId)).thenReturn(clusterNodePathsFrom);
    service.editClusterNode(editDto);
    verify(pathRepository, times(1)).findByClusterNodeIdFrom(clusterNodeId);
    verify(pathRepository, times(1)).findByClusterNodeIdTo(clusterNodeId);
    verify(pathRepository, times(1)).deleteAll(Collections.emptyList());
    verify(pathRepository, times(1)).saveAll(any());
  }
}
