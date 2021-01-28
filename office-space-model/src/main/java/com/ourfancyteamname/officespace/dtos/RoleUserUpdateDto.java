package com.ourfancyteamname.officespace.dtos;

import java.util.List;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserUpdateDto {
  private RoleDto roleDto;
  private UserDto userDto;
  private List<PermissionDto> permissionDto;
  private List<String> users;
  private List<String> roles;
}
