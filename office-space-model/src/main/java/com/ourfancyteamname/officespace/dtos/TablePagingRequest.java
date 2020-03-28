package com.ourfancyteamname.officespace.dtos;

import lombok.Data;

@Data
public class TablePagingRequest {
  private Integer page;
  private Integer pageSize;
}
