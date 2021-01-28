package com.ourfancyteamname.officespace.db.converters.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.dtos.PermissionDto;

@Mapper
public interface PermissionConverter {

  @Mapping(expression = "java(permission.getCode().getName())", target = "code")
  PermissionDto toDto(Permission permission);
}
