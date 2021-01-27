package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.view.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

  @InjectMocks
  private RoleServiceImpl service;

  @Mock
  private RoleRepository roleRepository;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<RoleUserListView> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Mock
  private RoleUserListViewRepository roleUserListViewRepository;

  @Test
  void create_dup() {
    Mockito.when(roleRepository.existsByCode("SUPER_ADMIN"))
        .thenReturn(true);
    var data = RoleDto.builder().authority("SUPER_ADMIN").build();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.createRole(data),
        ErrorCode.DUPLICATED.name());
  }

  @Test
  void create() {
    Mockito.when(roleRepository.existsByCode("SUPER_ADMIN"))
        .thenReturn(false);
    service.createRole(RoleDto.builder().authority("SUPER_ADMIN").build());
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void updateRole_notFound() {
    Mockito.when(roleRepository.findById(1))
        .thenReturn(Optional.empty());
    var data = RoleDto.builder().id(1).authority("SUPER_ADMIN").build();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.updateRole(data),
        ErrorCode.NOT_FOUND.name()
    );
  }

  @Test
  void updateRole_duplicate() {
    Role role1 = Role.builder().id(1).code("code").build();
    Role role2 = Role.builder().id(2).code("code2").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code2")).thenReturn(Optional.of(role2));
    var data = RoleDto.builder().id(1).authority("code2").build();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.updateRole(data),
        ErrorCode.DUPLICATED.name()
    );
  }

  @Test
  void delete() {
    service.deleteRole(1);
    Mockito.verify(roleRepository, Mockito.times(1)).deleteById(1);
  }

  @Test
  void updateRole_success() {
    Role role1 = Role.builder().id(1).code("code").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code2")).thenReturn(Optional.empty());
    service.updateRole(RoleDto.builder().id(1).authority("code2").build());
    Mockito.verify(roleRepository, Mockito.times(1)).findById(1);
    Mockito.verify(roleRepository, Mockito.times(1)).findByCode("code2");
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void updateRole_success2() {
    Role role1 = Role.builder().id(1).code("code").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code")).thenReturn(Optional.of(role1));
    service.updateRole(RoleDto.builder().id(1).authority("code").build());
    Mockito.verify(roleRepository, Mockito.times(1)).findById(1);
    Mockito.verify(roleRepository, Mockito.times(1)).findByCode("code");
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void getRoleCodes() {
    service.getRoleCodes();
    Mockito.verify(roleRepository, Mockito.times(1)).findAllCode();
  }

  @Test
  void getRoleUserListView() {
    var tableSearchRequest = TableSearchRequest.builder().build();
    var specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    var result = Collections.singletonList(RoleUserListView.builder().build());
    Mockito.when(paginationBuilderServiceImpl.from(tableSearchRequest)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, (Sort) null)).thenReturn(result);
    Page<RoleUserListView> roleUserListView = service.getRoleUserListView(tableSearchRequest);
    Assertions.assertEquals(1, roleUserListView.getTotalElements());
  }

}
