package com.ourfancyteamname.officespace.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TableSearchRequest {

  private String tableName;

  List<ColumnSearchRequest> columnSearchRequests;

}
