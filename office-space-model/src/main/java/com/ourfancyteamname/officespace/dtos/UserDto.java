package com.ourfancyteamname.officespace.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ourfancyteamname.officespace.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {

  private Integer id;

  private String username;

  private String password;

  private String email;

  private String firstName;

  private String lastName;

  private String phone;

  private String alternatePhone;

  private String address;

  private Gender gender;
}
