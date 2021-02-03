package com.ourfancyteamname.officespace.db.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

@Service(TableSearchBuilderService.SORT_QUALIFIER)
public class SortingBuilderServiceImpl implements TableSearchBuilderService<Sort> {

  @Override
  public Sort from(TableSearchRequest tableSearchRequest) {
    final var tableSortingRequest = tableSearchRequest.getSortingRequest();
    if (tableSortingRequest == null
        || StringUtils.isBlank(tableSortingRequest.getColumnName())
        || tableSortingRequest.getDirection() == null) {
      return Sort.unsorted();
    }
    return Sort.by(Sort.Direction.fromString(tableSortingRequest.getDirection().name()),
        tableSortingRequest.getColumnName());
  }

}
