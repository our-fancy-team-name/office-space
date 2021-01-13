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
public class ProductDto {
  private int id;
  private String partNumber;
  private String name;
  private String description;
  private String family;
  private String displayName;
  private Integer clusterId;
}
