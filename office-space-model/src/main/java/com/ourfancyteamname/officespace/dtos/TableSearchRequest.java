package com.ourfancyteamname.officespace.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TableSearchRequest {

  @Valid
  @NotNull
  private List<ColumnSearchRequest> columnSearchRequests;

  @Valid
  @NotNull
  private TablePagingRequest pagingRequest;

  @Valid
  @NotNull
  private TableSortingRequest sortingRequest;

}
