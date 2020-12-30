package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

  public Pageable getPage(TablePagingRequest tablepagingRequest, Sort sort) {
    if (tablepagingRequest == null) {
      return Pageable.unpaged();
    }
    return PageRequest.of(tablepagingRequest.getPage(), tablepagingRequest.getPageSize(), sort);
  }
}
