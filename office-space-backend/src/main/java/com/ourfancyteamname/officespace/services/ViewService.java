package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@FunctionalInterface
public interface ViewService<E> {

  Page<E> findAll(TableSearchRequest tableSearchRequest);

  default <D> Page<D> findAll(TableSearchRequest tableSearchRequest, Function<E, D> mapper) {
    return findAll(tableSearchRequest).map(mapper);
  }
}
