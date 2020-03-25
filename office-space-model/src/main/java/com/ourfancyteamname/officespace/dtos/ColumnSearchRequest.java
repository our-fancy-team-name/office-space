package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import lombok.Data;

@Data
public class ColumnSearchRequest {

  private String columnName;

  private DataBaseOperation operation;

  private String term;

  private boolean isOrTerm;

}
