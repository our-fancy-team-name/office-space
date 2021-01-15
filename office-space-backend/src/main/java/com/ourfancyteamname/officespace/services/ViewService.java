package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.function.Function;

public interface ViewService<E, R extends JpaSpecificationExecutor<E>> {

  R getExecutor();

  Page<E> findAll(TableSearchRequest tableSearchRequest);

  <D> Page<D> findAll(TableSearchRequest tableSearchRequest, Function<E, D> mapper);
}
