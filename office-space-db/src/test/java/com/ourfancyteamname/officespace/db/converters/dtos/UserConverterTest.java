package com.ourfancyteamname.officespace.db.converters.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.enums.Gender;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    assertEquals(userDto.getAddress(), entity.getAddress());
    assertEquals(userDto.getEmail(), entity.getEmail());
    assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    assertEquals(userDto.getFirstName(), entity.getFirstName());
    assertEquals(userDto.getLastName(), entity.getLastName());
    assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    assertEquals(userDto.getPhone(), entity.getPhone());
    assertSame(userDto.getGender(), entity.getGender());
  }

  @Test
  void toDto_null() {
    UserDto userDto = userConverter.toDto(null);
    assertNull(userDto);
  }

  @Test
  void toEntity_null() {
    User userDto = userConverter.toEntity(null);
    assertNull(userDto);
  }

  @Test
  void toEntity_sucess() {
    User userDto = userConverter.toEntity(UserDto.builder().build());
    assertNotNull(userDto);
  }

  @Test
  void toEntity_sucessWithId() {
    User userDto = userConverter.toEntity(UserDto.builder().id(1).build());
    assertNotNull(userDto);
  }
}
