package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.dtos.ClusterNodeEditDto;
import com.ourfancyteamname.officespace.dtos.GraphDto;

public interface ProcessService {

  GraphDto getGraph(Integer clusterId);

  ClusterNode addNodeToCluster(GraphDto graphDto);

  void addSinglePath(Integer clusterIdFrom, Integer clusterIdTo);

  void removePath(Integer pathId);

  void removeNodeFromCluster(Integer clusterNodeId);

  void editClusterNode(ClusterNodeEditDto clusterNodeEditDto);

}
