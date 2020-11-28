package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.dtos.UserConverter;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.enums.Gender;
import com.ourfancyteamname.officespace.db.entities.User;

import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserConverterTest {

  private UserConverter userConverter = Mappers.getMapper(UserConverter.class);

  @Test
  public void toDto_success() {
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
    Assert.assertEquals(userDto.getAddress(), entity.getAddress());
    Assert.assertEquals(userDto.getEmail(), entity.getEmail());
    Assert.assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    Assert.assertEquals(userDto.getFirstName(), entity.getFirstName());
    Assert.assertEquals(userDto.getLastName(), entity.getLastName());
    Assert.assertEquals(userDto.getAlternatePhone(), entity.getAlternatePhone());
    Assert.assertEquals(userDto.getPassword(), entity.getPassword());
    Assert.assertEquals(userDto.getPhone(), entity.getPhone());
    Assert.assertTrue(userDto.getGender() == entity.getGender());
  }
}
