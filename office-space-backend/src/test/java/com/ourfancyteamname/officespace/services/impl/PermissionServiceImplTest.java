package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyIterable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RolePermissionRepository;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PermissionServiceImplTest {

  @InjectMocks
  private PermissionServiceImpl service;

  @Mock
  private RolePermissionRepository rolePermissionRepository;

  @Mock
  private PermissionRepository permissionRepository;

  @Mock
  private EntityManager entityManager;

  @Mock
  private PermissionConverter permissionConverter;

  @Test
  void updateRolePermission() {
    RoleDto roleDto = RoleDto.builder().id(1).build();
    List<PermissionDto> permissionDtos = Collections.singletonList(PermissionDto.builder()
        .code(PermissionCode.PRCS_EDIT.name())
        .build());
    mockReturn(permissionRepository.findByCode(PermissionCode.PRCS_EDIT),
        Optional.of(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    service.updateRolePermission(roleDto, permissionDtos);
    verifyInvoke1Time(entityManager).flush();
    verifyInvoke1Time(rolePermissionRepository).removeByRoleId(1);
    verifyInvoke1Time(rolePermissionRepository).saveAll(anyIterable());
  }

  @Test
  void createRolePermission() {
    Role roleDto = Role.builder().id(1).build();
    List<PermissionDto> permissionDtos = Collections.singletonList(PermissionDto.builder()
        .code(PermissionCode.PRCS_EDIT.name())
        .build());
    mockReturn(permissionRepository.findByCode(PermissionCode.PRCS_EDIT),
        Optional.of(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    service.createRolePermission(roleDto, permissionDtos);
    verifyInvoke1Time(rolePermissionRepository).saveAll(anyIterable());
  }

  @Test
  void findAllPermissionByRole() {
    String role = "SUPER_ADMIN";
    List<Permission> permissions =
        Collections.singletonList(Permission.builder().code(PermissionCode.PRCS_EDIT).build());
    mockReturn(permissionRepository.findPermissionByRole(role), permissions);
    service.findAllPermissionByRole(role);
    verifyInvoke1Time(permissionConverter).toDto(any());
  }
}
