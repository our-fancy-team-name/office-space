package com.ourfancyteamname.officespace.services;

import java.util.List;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

public interface PermissionService {

  List<RolePermission> updateRolePermission(RoleDto role, List<PermissionDto> perm);

  List<RolePermission> createRolePermission(Role role, List<PermissionDto> perm);

  List<PermissionDto> findAllPermissionByRole(String role);

  void deleteRolePermissionByRoleId(Integer roleId);
}
