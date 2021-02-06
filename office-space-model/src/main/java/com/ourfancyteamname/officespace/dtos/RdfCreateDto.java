package com.ourfancyteamname.officespace.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RdfCreateDto {
  private RdfObject subject;
  private RdfObject predicate;
  private RdfObject object;
}
