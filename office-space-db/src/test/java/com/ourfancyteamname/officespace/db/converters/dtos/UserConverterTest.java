package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.enums.Gender;
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
    Assert.assertEquals(userDto.getPhone(), entity.getPhone());
    Assert.assertSame(userDto.getGender(), entity.getGender());
  }

  @Test
  public void toDto_null() {
    UserDto userDto = userConverter.toDto(null);
    Assert.assertNull(userDto);
  }

  @Test
  public void toEntity_null() {
    User userDto = userConverter.toEntity(null);
    Assert.assertNull(userDto);
  }

  @Test
  public void toEntity_sucess() {
    User userDto = userConverter.toEntity(UserDto.builder().build());
    Assert.assertNotNull(userDto);
  }

  @Test
  public void toEntity_sucessWithId() {
    User userDto = userConverter.toEntity(UserDto.builder().id(1).build());
    Assert.assertNotNull(userDto);
  }
}
