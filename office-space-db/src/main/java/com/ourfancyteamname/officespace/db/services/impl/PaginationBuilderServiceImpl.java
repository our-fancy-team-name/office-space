package com.ourfancyteamname.officespace.db.services.impl;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationBuilderServiceImpl implements TableSearchBuilderService<Pageable> {

  @Autowired
  @Qualifier("sortingBuilderServiceImpl")
  private TableSearchBuilderService<Sort> sortingBuilderService;

  @Override
  public Pageable from(TableSearchRequest tableSearchRequest) {
    var tablePagingRequest = tableSearchRequest.getPagingRequest();
    if (tablePagingRequest == null) {
      return Pageable.unpaged();
    }
    return PageRequest.of(tablePagingRequest.getPage(), tablePagingRequest.getPageSize(),
        sortingBuilderService.from(tableSearchRequest));
  }
}
