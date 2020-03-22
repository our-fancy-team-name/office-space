package com.ourfancyteamname.officespace.dtos;

import lombok.Data;

@Data
public class UserDto {

  private String username;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private String phone;

  private String alternatePhone;

  private String address;

  private String gender;
}
