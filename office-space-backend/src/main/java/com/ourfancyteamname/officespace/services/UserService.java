package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import org.springframework.data.domain.Page;

import java.util.List;

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
   * @param roleId
   */
  void deleteUserRoleByRoleId(Integer roleId);

  /**
   * This is just technical delete
   *
   * @param userId
   */
  void deleteUserRoleByUserId(Integer userId);
}
