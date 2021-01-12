package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import com.ourfancyteamname.officespace.dtos.ProcessPackageDto;

import java.util.List;

public interface ProcessPackageService {

  List<ProcessListView> getValidPksToAdd(Integer clusterNodeId);

  void addPkgToCltNode(ProcessPackageDto processPackageDto);
}
