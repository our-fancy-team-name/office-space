package com.ourfancyteamname.officespace.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TableSearchRequest {

  private List<ColumnSearchRequest> columnSearchRequests;

  private TablePagingRequest pagingRequest;

  private TableSortingRequest sortingRequest;

}
