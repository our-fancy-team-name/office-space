package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;

public interface ProcessService {

  GraphDto getGraph(Integer clusterId);

  ClusterNode addNodeToCluster(GraphDto graphDto);

  void removeNodeFromCluster(Integer clusterNodeId);

  void editClusterNode(ClusterNodeEditDto clusterNodeEditDto);

}
