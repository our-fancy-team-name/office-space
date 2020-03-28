package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import lombok.Data;

@Data
public class TableSortingRequest {
  private String columnName;
  private DataBaseDirection direction;
}
