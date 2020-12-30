package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.domain.Page;

public interface ClusterService {

  ProcessCluster create(ProcessGeneralDto processGeneralDto);

  Page<ProcessGeneralDto> getListView(TableSearchRequest tableSearchRequest);

  ProcessCluster update(ProcessGeneralDto processGeneralDto);
}
