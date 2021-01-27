package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public abstract class AbstractViewServiceImpl<E, R extends JpaSpecificationExecutor<E>> implements ViewService<E> {

  @Autowired
  @Qualifier("sortingBuilderServiceImpl")
  private TableSearchBuilderService<Sort> sortingBuilderService;

  @Autowired
  @Qualifier("paginationBuilderServiceImpl")
  private TableSearchBuilderService<Pageable> paginationBuilderService;

  @Autowired
  @Qualifier("specificationBuilderServiceImpl")
  private TableSearchBuilderService<Specification<E>> specificationBuilderService;

  protected abstract R getExecutor();

  @Override
  public Page<E> findAll(TableSearchRequest tableSearchRequest) {
    final var specification = specificationBuilderService.from(tableSearchRequest);
    final var pageable = paginationBuilderService.from(tableSearchRequest);
    if (pageable.isUnpaged()) {
      return new PageImpl<>(
          this.getExecutor().findAll(specification, sortingBuilderService.from(tableSearchRequest)));
    }
    return this.getExecutor().findAll(specification, pageable);
  }

}
