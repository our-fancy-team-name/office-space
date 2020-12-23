package com.ourfancyteamname.officespace.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessGeneralDto {
  private int id;
  private String code;
  private String name;
  private String description;
  private Integer clusterNodeIdTo;
  private Integer clusterNodeIdFrom;
  private String label;

  public ProcessGeneralDto(int id, String code, String name, String description) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.description = description;
  }
}
