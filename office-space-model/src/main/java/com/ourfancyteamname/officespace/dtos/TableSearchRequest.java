package com.ourfancyteamname.officespace.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TableSearchRequest {

  @NotBlank
  private String tableName;

  @Valid
  @NotNull
  List<ColumnSearchRequest> columnSearchRequests;

}
