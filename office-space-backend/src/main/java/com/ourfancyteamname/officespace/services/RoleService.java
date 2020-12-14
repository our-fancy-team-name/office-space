package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
  Page<RoleUserListView> getRoleUserListView(TableSearchRequest tableSearchRequest);

  Role updateRole(RoleDto roleDto);

  Role createRole(RoleDto roleDto);

  void deleteRole(Integer role);

  List<String> getRoleCodes();
}
