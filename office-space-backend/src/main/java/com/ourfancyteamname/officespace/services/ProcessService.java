package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.dtos.GraphDto;

public interface ProcessService {

  GraphDto getGraph(Integer clusterId);

  ClusterNode addNodeToCluster(GraphDto graphDto);

}
