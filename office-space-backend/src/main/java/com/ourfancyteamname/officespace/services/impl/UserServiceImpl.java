package com.ourfancyteamname.officespace.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ourfancyteamname.officespace.db.converters.dtos.UserConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.db.repos.UserRoleRepository;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.UserService;
import com.ourfancyteamname.officespace.services.ViewService;

@Service
public class UserServiceImpl extends AbstractViewServiceImpl<User, UserRepository> implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserConverter userConverter;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ViewService<UserRoleListView> userRoleListViewServiceImpl;

  @Override
  public Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest) {
    return findAll(tableSearchRequest, userConverter::toDto);
  }

  @Override
  public UserDto findById(Integer userId) {
    Assert.notNull(userId, ErrorCode.NOT_FOUND.name());
    return userRepository.findById(userId).map(userConverter::toDto)
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
  }

  @Override
  public User editUser(UserDto userDto) {
    final var target = userRepository.findById(userDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
    if (!userRepository.findByUsername(userDto.getUsername())
        .map(User::getId)
        .map(id -> target.getId() == id)
        .orElse(true)) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    target.setAddress(userDto.getAddress());
    target.setAlternatePhone(userDto.getAlternatePhone());
    target.setEmail(userDto.getEmail());
    target.setFirstName(userDto.getFirstName());
    target.setLastName(userDto.getLastName());
    target.setGender(userDto.getGender());
    target.setPhone(userDto.getPhone());
    target.setUsername(userDto.getUsername());
    return userRepository.save(target);
  }

  @Override
  public User createUser(UserDto userDto) {
    Assert.isTrue(!userRepository.existsByUsername(userDto.getUsername()), ErrorCode.DUPLICATED.name());
    final var user = userConverter.toEntity(userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public List<UserRole> updateUserRole(RoleDto roleDto, List<String> users) {
    deleteUserRoleByRoleId(roleDto.getId());
    entityManager.flush();
    final var userRoles = users.stream()
        .map(userRepository::findByUsername)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(User::getId)
        .map(userId -> UserRole.builder().roleId(roleDto.getId()).userId(userId).isRecentlyUse(false).build())
        .collect(Collectors.toList());
    return userRoleRepository.saveAll(userRoles);
  }

  @Override
  public List<UserRole> updateRoleUser(UserDto userDto, List<String> roles) {
    deleteUserRoleByUserId(userDto.getId());
    entityManager.flush();
    final var userRoles = roles.stream()
        .map(roleRepository::findByCode)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(Role::getId)
        .map(roleId -> UserRole.builder().roleId(roleId).userId(userDto.getId()).isRecentlyUse(false).build())
        .collect(Collectors.toList());
    return userRoleRepository.saveAll(userRoles);
  }

  @Override
  public List<UserRole> createRoleUser(User user, List<String> roles) {
    return userRoleRepository.saveAll(ListUtils.emptyIfNull(roles).stream()
        .map(roleRepository::findByCode)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(Role::getId)
        .map(roleId -> UserRole.builder()
            .userId(user.getId())
            .isRecentlyUse(false)
            .roleId(roleId)
            .build())
        .collect(Collectors.toList()));
  }

  @Override
  public List<UserRole> createUserRole(Role role, List<String> users) {
    return userRoleRepository.saveAll(ListUtils.emptyIfNull(users).stream()
        .map(userRepository::findByUsername)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(User::getId)
        .map(userId -> UserRole.builder().roleId(role.getId()).userId(userId).isRecentlyUse(false).build())
        .collect(Collectors.toList()));
  }

  @Override
  public void removeUser(Integer userId) {
    deleteUserRoleByUserId(userId);
    entityManager.flush();
    userRepository.deleteById(userId);
  }

  @Override
  public Page<UserRoleListView> findUserRoleListView(TableSearchRequest tableSearchRequest) {
    return userRoleListViewServiceImpl.findAll(tableSearchRequest);
  }

  @Override
  public void deleteUserRoleByRoleId(Integer roleId) {
    userRoleRepository.removeByRoleId(roleId);
  }

  @Override
  public void deleteUserRoleByUserId(Integer userId) {
    userRoleRepository.removeByUserId(userId);
  }

  @Override
  protected UserRepository getExecutor() {
    return this.userRepository;
  }
}
