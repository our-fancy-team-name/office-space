package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnSearchRequest {

  private String columnName;

  private DataBaseOperation operation;

  private String term;

  private boolean isOrTerm;

}
