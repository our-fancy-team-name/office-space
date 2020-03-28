package com.ourfancyteamname.officespace.postgres.services;

import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortingService {

  public Sort getSort(TableSortingRequest tableSortingRequest) {
    if (StringUtils.isBlank(tableSortingRequest.getColumnName())
        || tableSortingRequest.getDirection() == null) {
      return Sort.unsorted();
    }
    return Sort.by(Sort.Direction.fromString(tableSortingRequest.getDirection().name()),
        tableSortingRequest.getColumnName());
  }

}
