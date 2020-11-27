package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.db.entities.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleConverter {

  @Mapping(source = "role.code", target = "authority")
  @Mapping(expression = "java(currentlyUse.getId() == role.getId())", target = "isUsing")
  RoleDto toDto(Role role, Role currentlyUse);
}
