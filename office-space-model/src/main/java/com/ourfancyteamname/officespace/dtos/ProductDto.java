package com.ourfancyteamname.officespace.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
  private int id;
  private String partNumber;
  private String name;
  private String description;
  private String family;
}
