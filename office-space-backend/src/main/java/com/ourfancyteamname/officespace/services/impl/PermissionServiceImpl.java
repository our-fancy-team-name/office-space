package com.ourfancyteamname.officespace.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RolePermissionRepository;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.services.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  private RolePermissionRepository rolePermissionRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private PermissionConverter permissionConverter;

  @Override
  public List<RolePermission> updateRolePermission(RoleDto role, List<PermissionDto> perm) {
    deleteRolePermissionByRoleId(role.getId());
    entityManager.flush();
    final var target = perm.stream()
        .map(PermissionDto::getCode)
        .map(PermissionCode::valueOf)
        .map(permissionRepository::findByCode)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(p -> RolePermission.builder()
            .permissionId(p.getId())
            .roleId(role.getId())
            .build())
        .collect(Collectors.toList());
    return rolePermissionRepository.saveAll(target);
  }

  @Override
  public List<RolePermission> createRolePermission(Role role, List<PermissionDto> perm) {
    final var target = perm.stream()
        .map(PermissionDto::getCode)
        .map(PermissionCode::valueOf)
        .map(permissionRepository::findByCode)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(p -> RolePermission.builder()
            .permissionId(p.getId())
            .roleId(role.getId())
            .build())
        .collect(Collectors.toList());
    return rolePermissionRepository.saveAll(target);
  }

  @Override
  public List<PermissionDto> findAllPermissionByRole(String role) {
    return permissionRepository.findPermissionByRole(role)
        .stream()
        .map(permissionConverter::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteRolePermissionByRoleId(Integer roleId) {
    rolePermissionRepository.removeByRoleId(roleId);
  }

}
