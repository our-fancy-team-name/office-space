package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ViewService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public abstract class AbstractViewServiceImpl<E, R extends JpaSpecificationExecutor<E>> implements ViewService<E> {

  @Autowired
  private SortingBuilderService sortingBuilderService;

  @Autowired
  private PaginationBuilderService paginationBuilderService;

  @Autowired
  private SpecificationBuilderService<E> specificationBuilderService;

  protected abstract R getExecutor();

  @Override
  public Page<E> findAll(TableSearchRequest tableSearchRequest) {
    val specification = specificationBuilderService.from(tableSearchRequest);
    val sort = sortingBuilderService.from(tableSearchRequest.getSortingRequest());
    val pageable = paginationBuilderService.from(tableSearchRequest.getPagingRequest(), sort);
    if (pageable.isUnpaged()) {
      return new PageImpl<>(this.getExecutor().findAll(specification, sort));
    }
    return this.getExecutor().findAll(specification, pageable);
  }

}
