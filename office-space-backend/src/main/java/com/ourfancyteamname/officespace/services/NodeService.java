package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.domain.Page;

public interface NodeService {

  ProcessNode create(ProcessGeneralDto processGeneralDto);

  Page<ProcessGeneralDto> getListView(TableSearchRequest tableSearchRequest);
}
