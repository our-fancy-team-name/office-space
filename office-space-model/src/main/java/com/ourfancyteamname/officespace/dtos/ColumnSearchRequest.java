package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ColumnSearchRequest {

  @NotBlank
  private String columnName;

  @NotNull
  private DataBaseOperation operation;

  @NotBlank
  private String term;

  private boolean isOrTerm;

}
