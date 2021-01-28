package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalNotFound;
import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvokeTime;
import static org.mockito.Mockito.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePackage;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePackageRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;
import com.ourfancyteamname.officespace.enums.PackageStatus;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProcessPackageServiceImplTest {

  @InjectMocks
  private ProcessPackageServiceImpl service;

  @Mock
  private ClusterNodePathRepository clusterNodePathRepository;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Mock
  private ClusterNodeRepository clusterNodeRepository;

  @Mock
  private ClusterNodePackageRepository clusterNodePackageRepository;

  @Test
  void getValidPksToAdd_notFoundSchematic() {
    mockReturn(clusterNodeRepository.getClusterSchematic(1), Optional.empty());
    assertThrowIllegalNotFound(() -> service.getValidPksToAdd(1));
  }

  @Test
  void getValidPksToAdd_getPckFromMiddleNode() {
    String clusterSchematic = "PRD_1";
    int clusterNodeId = 1;
    mockReturn(clusterNodeRepository.getClusterSchematic(clusterNodeId), Optional.of(clusterSchematic));
    mockReturn(clusterNodePathRepository.existsByClusterNodeIdTo(clusterNodeId), true);
    service.getValidPksToAdd(clusterNodeId);
    verifyInvoke1Time(processListViewRepository)
        .findPossiblePkgsOnMiddleNode(String.valueOf(clusterNodeId), clusterSchematic);
    verifyInvokeTime(processListViewRepository, 0).findPossiblePkgsOnStartNode(clusterSchematic);
    verifyInvoke1Time(clusterNodeRepository).getClusterSchematic(clusterNodeId);
    verifyInvoke1Time(clusterNodePathRepository).existsByClusterNodeIdTo(clusterNodeId);
  }

  @Test
  void getValidPksToAdd_getPckFromStartNode() {
    String clusterSchematic = "PRD_1";
    int clusterNodeId = 1;
    mockReturn(clusterNodeRepository.getClusterSchematic(clusterNodeId), Optional.of(clusterSchematic));
    mockReturn(clusterNodePathRepository.existsByClusterNodeIdTo(clusterNodeId), false);
    service.getValidPksToAdd(clusterNodeId);
    verifyInvoke1Time(processListViewRepository).findPossiblePkgsOnStartNode(clusterSchematic);
    verifyInvokeTime(processListViewRepository, 0)
        .findPossiblePkgsOnMiddleNode(String.valueOf(clusterNodeId), clusterSchematic);
    verifyInvoke1Time(clusterNodeRepository).getClusterSchematic(clusterNodeId);
    verifyInvoke1Time(clusterNodePathRepository).existsByClusterNodeIdTo(clusterNodeId);
  }

  @Test
  void addPkgToCltNode_edit() {
    ProcessPackageDto data = ProcessPackageDto.builder()
        .packageId(1)
        .clusterNodeId(1)
        .status(PackageStatus.PASS)
        .build();
    ClusterNodePackage data2 = ClusterNodePackage.builder()
        .packageId(data.getPackageId())
        .clusterNodeId(data.getClusterNodeId())
        .status(data.getStatus())
        .build();
    mockReturn(
        clusterNodePackageRepository
            .findByPackageIdAndClusterNodeIdAndStatus(data.getPackageId(), data.getClusterNodeId(), data.getStatus()),
        Optional.of(data2));
    service.addPkgToCltNode(data);
    verifyInvoke1Time(clusterNodePackageRepository).save(any());
  }

  @Test
  void addPkgToCltNode_create() {
    ProcessPackageDto data = ProcessPackageDto.builder()
        .packageId(1)
        .clusterNodeId(1)
        .status(PackageStatus.PASS)
        .build();
    mockReturn(
        clusterNodePackageRepository
            .findByPackageIdAndClusterNodeIdAndStatus(data.getPackageId(), data.getClusterNodeId(), data.getStatus()),
        Optional.empty());
    service.addPkgToCltNode(data);
    verifyInvoke1Time(clusterNodePackageRepository).save(any());
  }

}
