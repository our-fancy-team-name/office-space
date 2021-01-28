package com.ourfancyteamname.officespace.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ViewService;

public abstract class AbstractViewServiceImpl<E, R extends JpaSpecificationExecutor<E>> implements ViewService<E> {

  @Autowired
  private TableSearchBuilderService<Sort> sortingBuilderServiceImpl;

  @Autowired
  private TableSearchBuilderService<Pageable> paginationBuilderServiceImpl;

  @Autowired
  private TableSearchBuilderService<Specification<E>> specificationBuilderServiceImpl;

  protected abstract R getExecutor();

  @Override
  public Page<E> findAll(TableSearchRequest tableSearchRequest) {
    final var specification = specificationBuilderServiceImpl.from(tableSearchRequest);
    final var pageable = paginationBuilderServiceImpl.from(tableSearchRequest);
    if (pageable.isUnpaged()) {
      return new PageImpl<>(
          this.getExecutor().findAll(specification, sortingBuilderServiceImpl.from(tableSearchRequest)));
    }
    return this.getExecutor().findAll(specification, pageable);
  }

}
