package com.ourfancyteamname.officespace.postgres.converters;

import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.postgres.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {
  UserDto toDto(User user);
}
