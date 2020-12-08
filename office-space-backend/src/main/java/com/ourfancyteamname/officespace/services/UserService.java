package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

  Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest);
  List<PermissionDto> findAllPermissionByRole(String role);
  Page<RoleUserListView> getRolUserListView(TableSearchRequest tableSearchRequest);
}
