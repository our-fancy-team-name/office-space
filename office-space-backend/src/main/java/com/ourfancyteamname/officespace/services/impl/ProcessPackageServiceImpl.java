package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePackage;
import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePackageRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodePathRepository;
import com.ourfancyteamname.officespace.db.repos.ClusterNodeRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.ProcessPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessPackageServiceImpl implements ProcessPackageService {

  @Autowired
  private ClusterNodePathRepository clusterNodePathRepository;

  @Autowired
  private ProcessListViewRepository processListViewRepository;

  @Autowired
  private ClusterNodeRepository clusterNodeRepository;

  @Autowired
  private ClusterNodePackageRepository clusterNodePackageRepository;

  @Override
  public List<ProcessListView> getValidPksToAdd(Integer clusterNodeId) {
    String clusterSchematic = clusterNodeRepository.getClusterSchematic(clusterNodeId)
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
    if (!clusterNodePathRepository.existsByClusterNodeIdTo(clusterNodeId)) {
      return processListViewRepository.findPossiblePkgsOnStartNode(clusterSchematic);
    }
    return processListViewRepository
        .findPossiblePkgsOnMiddleNode(String.valueOf(clusterNodeId), clusterSchematic);
  }

  @Override
  public void addPkgToCltNode(ProcessPackageDto processPackageDto) {
    ClusterNodePackage target =
        clusterNodePackageRepository.findByPackageIdAndClusterNodeIdAndStatus(processPackageDto.getPackageId(),
            processPackageDto.getClusterNodeId(), processPackageDto.getStatus())
            .orElseGet(() -> ClusterNodePackage.builder().packageId(processPackageDto.getPackageId())
                .clusterNodeId(processPackageDto.getClusterNodeId())
                .status(processPackageDto.getStatus())
                .build());
    target.setAmount(processPackageDto.getAmount());
    clusterNodePackageRepository.save(target);
  }
}
