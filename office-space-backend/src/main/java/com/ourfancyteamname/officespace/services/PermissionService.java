package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

import java.util.List;

public interface PermissionService {
  void updateRolePermission(RoleDto role, List<PermissionDto> perm);
}
