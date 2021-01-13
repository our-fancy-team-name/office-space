package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePackage;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePackageRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;
import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProcessPackageServiceImplTest {

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

  @Test(expected = IllegalArgumentException.class)
  public void getValidPksToAdd_notFoundSchematic() {
    Mockito.when(clusterNodeRepository.getClusterSchematic(1)).thenReturn(Optional.empty());
    service.getValidPksToAdd(1);
  }

  @Test
  public void getValidPksToAdd_getPckFromMiddleNode() {
    String clusterSchematic = "PRD_1";
    int clusterNodeId = 1;
    Mockito.when(clusterNodeRepository.getClusterSchematic(clusterNodeId)).thenReturn(Optional.of(clusterSchematic));
    Mockito.when(clusterNodePathRepository.existsByClusterNodeIdTo(clusterNodeId)).thenReturn(true);
    service.getValidPksToAdd(clusterNodeId);
    Mockito.verify(processListViewRepository, Mockito.times(1))
        .findPossiblePkgsOnMiddleNode(String.valueOf(clusterNodeId), clusterSchematic);
    Mockito.verify(processListViewRepository, Mockito.times(0))
        .findPossiblePkgsOnStartNode(clusterSchematic);
    Mockito.verify(clusterNodeRepository, Mockito.times(1)).getClusterSchematic(clusterNodeId);
    Mockito.verify(clusterNodePathRepository, Mockito.times(1)).existsByClusterNodeIdTo(clusterNodeId);
  }

  @Test
  public void getValidPksToAdd_getPckFromStartNode() {
    String clusterSchematic = "PRD_1";
    int clusterNodeId = 1;
    Mockito.when(clusterNodeRepository.getClusterSchematic(clusterNodeId)).thenReturn(Optional.of(clusterSchematic));
    Mockito.when(clusterNodePathRepository.existsByClusterNodeIdTo(clusterNodeId)).thenReturn(false);
    service.getValidPksToAdd(clusterNodeId);
    Mockito.verify(processListViewRepository, Mockito.times(1))
        .findPossiblePkgsOnStartNode(clusterSchematic);
    Mockito.verify(processListViewRepository, Mockito.times(0))
        .findPossiblePkgsOnMiddleNode(String.valueOf(clusterNodeId), clusterSchematic);
    Mockito.verify(clusterNodeRepository, Mockito.times(1)).getClusterSchematic(clusterNodeId);
    Mockito.verify(clusterNodePathRepository, Mockito.times(1)).existsByClusterNodeIdTo(clusterNodeId);
  }

  @Test
  public void addPkgToCltNode_edit() {
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
    Mockito.when(clusterNodePackageRepository
        .findByPackageIdAndClusterNodeIdAndStatus(data.getPackageId(), data.getClusterNodeId(), data.getStatus()))
        .thenReturn(Optional.of(data2));
    service.addPkgToCltNode(data);
    Mockito.verify(clusterNodePackageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void addPkgToCltNode_create() {
    ProcessPackageDto data = ProcessPackageDto.builder()
        .packageId(1)
        .clusterNodeId(1)
        .status(PackageStatus.PASS)
        .build();
    Mockito.when(clusterNodePackageRepository
        .findByPackageIdAndClusterNodeIdAndStatus(data.getPackageId(), data.getClusterNodeId(), data.getStatus()))
        .thenReturn(Optional.empty());
    service.addPkgToCltNode(data);
    Mockito.verify(clusterNodePackageRepository, Mockito.times(1)).save(Mockito.any());
  }

}
