package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserConverterTest {

  private final UserConverter userConverter = Mappers.getMapper(UserConverter.class);

  @Test
  void toDto_success() {
    User entity = User.builder()
        .email("dang")
        .id(1)
        .password("pw")
        .username("username")
        .address("address")
        .alternatePhone("alternatePhone")
        .firstName("firstName")
        .lastName("lastName")
        .gender(Gender.MALE)
        .phone("phone")
        .build();
    UserDto userDto = userConverter.toDto(entity);
    Assertions.assertEquals(userDto.getAddress(), entity.getAddress());
    Assertions.assertEquals(userDto.getEmail(), entity.getEmail());
    Assertions.assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    Assertions.assertEquals(userDto.getFirstName(), entity.getFirstName());
    Assertions.assertEquals(userDto.getLastName(), entity.getLastName());
    Assertions.assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    Assertions.assertEquals(userDto.getPhone(), entity.getPhone());
    Assertions.assertSame(userDto.getGender(), entity.getGender());
  }

  @Test
  void toDto_null() {
    UserDto userDto = userConverter.toDto(null);
    Assertions.assertNull(userDto);
  }

  @Test
  void toEntity_null() {
    User userDto = userConverter.toEntity(null);
    Assertions.assertNull(userDto);
  }

  @Test
  void toEntity_sucess() {
    User userDto = userConverter.toEntity(UserDto.builder().build());
    Assertions.assertNotNull(userDto);
  }

  @Test
  void toEntity_sucessWithId() {
    User userDto = userConverter.toEntity(UserDto.builder().id(1).build());
    Assertions.assertNotNull(userDto);
  }
}
