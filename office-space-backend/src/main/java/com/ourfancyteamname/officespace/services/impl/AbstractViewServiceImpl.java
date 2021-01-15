package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.function.Function;

public abstract class AbstractViewServiceImpl<E, R extends JpaSpecificationExecutor<E>> implements ViewService<E> {

  @Autowired
  private SortingBuilderService sortingBuilderService;

  @Autowired
  private PaginationBuilderService paginationBuilderService;

  @Autowired
  private SpecificationBuilderService specificationBuilderService;

  protected abstract R getExecutor();

  @Override
  public Page<E> findAll(TableSearchRequest tableSearchRequest) {
    final Specification<E> specification = specificationBuilderService.from(tableSearchRequest);
    final Sort sort = sortingBuilderService.from(tableSearchRequest.getSortingRequest());
    final Pageable pageable = paginationBuilderService.from(tableSearchRequest.getPagingRequest(), sort);
    if (pageable.isUnpaged()) {
      return new PageImpl<>(this.getExecutor().findAll(specification, sort));
    }
    return this.getExecutor().findAll(specification, pageable);
  }

  @Override
  public <D> Page<D> findAll(TableSearchRequest tableSearchRequest, Function<E, D> mapper) {
    return this.findAll(tableSearchRequest).map(mapper);
  }
}
