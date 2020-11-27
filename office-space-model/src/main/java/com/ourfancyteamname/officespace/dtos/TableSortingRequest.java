package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TableSortingRequest {
  private String columnName;
  private DataBaseDirection direction;
}
