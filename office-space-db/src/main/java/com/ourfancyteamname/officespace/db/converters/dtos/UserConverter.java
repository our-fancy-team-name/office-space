package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {

  @Mapping(target = "password", ignore = true)
  UserDto toDto(User user);

  @Mapping(target = "password", ignore = true)
  User toEntity(UserDto userDto);
}
