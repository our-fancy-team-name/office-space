package com.ourfancyteamname.officespace.db.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

@Service(TableSearchBuilderService.PAGE_QUALIFIER)
public class PaginationBuilderServiceImpl implements TableSearchBuilderService<Pageable> {

  @Autowired
  private TableSearchBuilderService<Sort> sortingBuilderServiceImpl;

  @Override
  public Pageable from(TableSearchRequest tableSearchRequest) {
    final var tablePagingRequest = tableSearchRequest.getPagingRequest();
    if (tablePagingRequest == null) {
      return Pageable.unpaged();
    }
    return PageRequest.of(tablePagingRequest.getPage(), tablePagingRequest.getPageSize(),
        sortingBuilderServiceImpl.from(tableSearchRequest));
  }
}
