package com.ourfancyteamname.officespace.dtos;

import com.ourfancyteamname.officespace.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

  private Integer id;

  private String username;

  private String email;

  private String firstName;

  private String lastName;

  private String phone;

  private String alternatePhone;

  private String address;

  private Gender gender;
}
