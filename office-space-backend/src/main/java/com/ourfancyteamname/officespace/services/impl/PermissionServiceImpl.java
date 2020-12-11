package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RolePermissionRepository;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.services.PermissionService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  private RolePermissionRepository rolePermissionRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void updateRolePermission(RoleDto role, List<PermissionDto> perm) {
    rolePermissionRepository.removeByRoleId(role.getId());
    entityManager.flush();
    List<RolePermission> target = perm.stream()
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
    rolePermissionRepository.saveAll(target);
  }

  public static void main(String[] args) {
    PermissionCode a = PermissionCode.valueOf("USER_EDIT");
  }
}
