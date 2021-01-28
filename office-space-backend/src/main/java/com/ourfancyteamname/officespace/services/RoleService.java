package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

public interface RoleService {
  Page<RoleUserListView> getRoleUserListView(TableSearchRequest tableSearchRequest);

  Role updateRole(RoleDto roleDto);

  Role createRole(RoleDto roleDto);

  void deleteRole(Integer role);

  List<String> getRoleCodes();
}
