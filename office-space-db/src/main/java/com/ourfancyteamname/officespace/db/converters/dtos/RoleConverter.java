package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleConverter {

  @Mapping(source = "role.code", target = "authority")
  @Mapping(expression = "java(currentlyUse != null " +
      "&& role != null " +
      "&& currentlyUse.getId() == role.getId())", target = "isUsing")
  @Mapping(source = "role.description", target = "description")
  @Mapping(source = "role.id", target = "id")
  RoleDto toDto(Role role, Role currentlyUse);
}
