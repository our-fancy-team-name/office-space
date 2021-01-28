package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalDuplicated;
import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegalNotFound;
import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.view.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    mockReturn(roleRepository.existsByCode("SUPER_ADMIN"), true);
    var data = RoleDto.builder().authority("SUPER_ADMIN").build();
    assertThrowIllegalDuplicated(() -> service.createRole(data));
  }

  @Test
  void create() {
    mockReturn(roleRepository.existsByCode("SUPER_ADMIN"), false);
    service.createRole(RoleDto.builder().authority("SUPER_ADMIN").build());
    verifyInvoke1Time(roleRepository).save(any());
  }

  @Test
  void updateRole_notFound() {
    mockReturn(roleRepository.findById(1), Optional.empty());
    var data = RoleDto.builder().id(1).authority("SUPER_ADMIN").build();
    assertThrowIllegalNotFound(() -> service.updateRole(data));
  }

  @Test
  void updateRole_duplicate() {
    Role role1 = Role.builder().id(1).code("code").build();
    Role role2 = Role.builder().id(2).code("code2").build();
    mockReturn(roleRepository.findById(1), Optional.of(role1));
    mockReturn(roleRepository.findByCode("code2"), Optional.of(role2));
    var data = RoleDto.builder().id(1).authority("code2").build();
    assertThrowIllegalDuplicated(() -> service.updateRole(data));
  }

  @Test
  void delete() {
    service.deleteRole(1);
    verifyInvoke1Time(roleRepository).deleteById(1);
  }

  @Test
  void updateRole_success() {
    Role role1 = Role.builder().id(1).code("code").build();
    mockReturn(roleRepository.findById(1), Optional.of(role1));
    mockReturn(roleRepository.findByCode("code2"), Optional.empty());
    service.updateRole(RoleDto.builder().id(1).authority("code2").build());
    verifyInvoke1Time(roleRepository).findById(1);
    verifyInvoke1Time(roleRepository).findByCode("code2");
    verifyInvoke1Time(roleRepository).save(any());
  }

  @Test
  void updateRole_success2() {
    Role role1 = Role.builder().id(1).code("code").build();
    mockReturn(roleRepository.findById(1), Optional.of(role1));
    mockReturn(roleRepository.findByCode("code"), Optional.of(role1));
    service.updateRole(RoleDto.builder().id(1).authority("code").build());
    verifyInvoke1Time(roleRepository).findById(1);
    verifyInvoke1Time(roleRepository).findByCode("code");
    verifyInvoke1Time(roleRepository).save(any());
  }

  @Test
  void getRoleCodes() {
    service.getRoleCodes();
    verifyInvoke1Time(roleRepository).findAllCode();
  }

  @Test
  void getRoleUserListView() {
    var tableSearchRequest = TableSearchRequest.builder().build();
    var specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    var result = Collections.singletonList(RoleUserListView.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), Pageable.unpaged());
    mockReturn(service.getExecutor().findAll(specs, (Sort) null), result);
    var roleUserListView = service.getRoleUserListView(tableSearchRequest);
    assertEquals(1, roleUserListView.getTotalElements());
  }

}
