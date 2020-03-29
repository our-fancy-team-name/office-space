package com.ourfancyteamname.officespace.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TablePagingRequest {
  private Integer page;
  private Integer pageSize;
}
