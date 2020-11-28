package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.db.entities.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {
  UserDto toDto(User user);
}
