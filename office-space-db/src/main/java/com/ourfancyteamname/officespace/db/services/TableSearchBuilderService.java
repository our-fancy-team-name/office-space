package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface TableSearchBuilderService<T> {
  String PAGE_QUALIFIER = "paginationBuilderService";
  String SORT_QUALIFIER = "sortingBuilderService";
  String SPECS_QUALIFIER = "specificationBuilderService";

  T from(TableSearchRequest tableSearchRequest);

}
