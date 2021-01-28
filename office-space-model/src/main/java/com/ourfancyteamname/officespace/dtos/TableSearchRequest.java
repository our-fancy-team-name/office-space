package com.ourfancyteamname.officespace.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSearchRequest {

  private List<ColumnSearchRequest> columnSearchRequests;

  private TablePagingRequest pagingRequest;

  private TableSortingRequest sortingRequest;

}
