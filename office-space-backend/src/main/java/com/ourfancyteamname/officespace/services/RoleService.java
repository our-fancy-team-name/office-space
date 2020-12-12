package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

public interface RoleService {
  Role updateRole(RoleDto roleDto);
  Role createRole(RoleDto roleDto);
}
