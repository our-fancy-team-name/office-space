package com.ourfancyteamname.officespace.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;

public interface UserService {

  Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest);

  UserDto findById(Integer userId);

  User editUser(UserDto userDto);

  User createUser(UserDto userDto);

  List<UserRole> updateUserRole(RoleDto roleDto, List<String> usernames);

  List<UserRole> createUserRole(Role roleDto, List<String> usernames);

  List<UserRole> updateRoleUser(UserDto userDto, List<String> roles);

  List<UserRole> createRoleUser(User user, List<String> roles);

  void removeUser(Integer userId);

  Page<UserRoleListView> findUserRoleListView(TableSearchRequest tableSearchRequest);

  /**
   * This just technical delete
   *
   * @param roleId role id
   */
  void deleteUserRoleByRoleId(Integer roleId);

  /**
   * This is just technical delete
   *
   * @param userId user id
   */
  void deleteUserRoleByUserId(Integer userId);
}
