package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.converters.dtos.UserConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.db.repos.UserRoleRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.db.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.services.UserService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private UserConverter userConverter;

  @Autowired
  private PermissionConverter permissionConverter;

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private RoleUserListViewRepository roleUserListViewRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest) {
    Specification<User> specs = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable page = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return userRepository.findAll(specs, page)
        .map(userConverter::toDto);
  }

  @Override
  public List<PermissionDto> findAllPermissionByRole(String role) {
    return permissionRepository.findPermissionByRole(role)
        .stream()
        .map(permissionConverter::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Page<RoleUserListView> getRolUserListView(TableSearchRequest tableSearchRequest) {
    Specification<RoleUserListView> specs = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable page = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return roleUserListViewRepository.findAll(specs, page);
  }

  @Override
  public List<UserRole> updateUserRole(RoleDto roleDto, List<String> users) {
    userRoleRepository.removeByRoleId(roleDto.getId());
    entityManager.flush();
    List<UserRole> userRoles = users.stream()
        .map(userRepository::findByUsername)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(User::getId)
        .map(userId -> UserRole.builder().roleId(roleDto.getId()).userId(userId).isRecentlyUse(false).build())
        .collect(Collectors.toList());
    return userRoleRepository.saveAll(userRoles);
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
}
