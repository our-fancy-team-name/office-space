package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

import java.util.List;

public interface PermissionService {
  List<RolePermission> updateRolePermission(RoleDto role, List<PermissionDto> perm);
  List<RolePermission> createRolePermission(Role role, List<PermissionDto> perm);
}
