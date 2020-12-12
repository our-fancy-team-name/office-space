package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import org.springframework.data.domain.Page;

public interface RoleService {
  Page<RoleUserListView> getRolUserListView(TableSearchRequest tableSearchRequest);

  Role updateRole(RoleDto roleDto);

  Role createRole(RoleDto roleDto);

  void deleteRole(Integer role);
}
