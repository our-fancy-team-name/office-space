package com.ourfancyteamname.officespace.postgres.converters;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.postgres.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface RoleConverter {

  @Mappings({
      @Mapping(source = "role.code", target = "authority"),
      @Mapping(expression = "java(currentlyUse.getId() == role.getId())", target = "isUsing")
  })
  RoleDto toDto(Role role, Role currentlyUse);
}
