package com.ourfancyteamname.officespace.services;

import java.util.List;

import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;

public interface ProcessPackageService {

  List<ProcessListView> getValidPksToAdd(Integer clusterNodeId);

  void addPkgToCltNode(ProcessPackageDto processPackageDto);
}
