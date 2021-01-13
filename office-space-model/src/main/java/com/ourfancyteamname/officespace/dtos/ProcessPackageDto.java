package com.ourfancyteamname.officespace.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ourfancyteamname.officespace.enums.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessPackageDto {
  private Integer packageId;
  private Integer clusterNodeId;
  private Integer amount;
  private PackageStatus status;
}
