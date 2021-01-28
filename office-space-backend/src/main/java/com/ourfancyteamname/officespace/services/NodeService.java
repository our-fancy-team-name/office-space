package com.ourfancyteamname.officespace.services;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface NodeService {

  ProcessNode create(ProcessGeneralDto processGeneralDto);

  ProcessNode update(ProcessGeneralDto processGeneralDto);

  Page<ProcessGeneralDto> getListView(TableSearchRequest tableSearchRequest);
}
