package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RolePermissionRepository;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
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
    Mockito.when(permissionRepository.findByCode(PermissionCode.PRCS_EDIT))
        .thenReturn(Optional.of(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    service.updateRolePermission(roleDto, permissionDtos);
    Mockito.verify(entityManager, Mockito.times(1)).flush();
    Mockito.verify(rolePermissionRepository, Mockito.times(1)).removeByRoleId(1);
    Mockito.verify(rolePermissionRepository, Mockito.times(1)).saveAll(Mockito.anyIterable());
  }

  @Test
  void createRolePermission() {
    Role roleDto = Role.builder().id(1).build();
    List<PermissionDto> permissionDtos = Collections.singletonList(PermissionDto.builder()
        .code(PermissionCode.PRCS_EDIT.name())
        .build());
    Mockito.when(permissionRepository.findByCode(PermissionCode.PRCS_EDIT))
        .thenReturn(Optional.of(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    service.createRolePermission(roleDto, permissionDtos);
    Mockito.verify(rolePermissionRepository, Mockito.times(1)).saveAll(Mockito.anyIterable());
  }

  @Test
  void findAllPermissionByRole() {
    String role = "SUPER_ADMIN";
    List<Permission> permissions =
        Collections.singletonList(Permission.builder().code(PermissionCode.PRCS_EDIT).build());
    Mockito.when(permissionRepository.findPermissionByRole(role))
        .thenReturn(permissions);
    service.findAllPermissionByRole(role);
    Mockito.verify(permissionConverter, Mockito.times(1)).toDto(Mockito.any());
  }
}
