package com.ourfancyteamname.officespace.postgres.converters;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.postgres.entities.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleConverter {

  public RoleDto toDto(Role role, Role currentlyUse) {
    return RoleDto.builder()
        .authority(role.getCode())
        .isUsing(role.getId() == currentlyUse.getId())
        .build();
  }
}
