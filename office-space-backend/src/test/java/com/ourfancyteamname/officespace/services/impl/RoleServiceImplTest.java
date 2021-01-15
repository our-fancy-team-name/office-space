package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.view.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

  @InjectMocks
  private RoleServiceImpl service;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private SpecificationBuilderService specificationBuilderService;

  @Mock
  private SortingBuilderService sortingBuilderService;

  @Mock
  private PaginationBuilderService paginationBuilderService;

  @Mock
  private RoleUserListViewRepository roleUserListViewRepository;

  @Test(expected = IllegalArgumentException.class)
  public void create_dup() {
    Mockito.when(roleRepository.existsByCode("SUPER_ADMIN"))
        .thenReturn(true);
    service.createRole(RoleDto.builder().authority("SUPER_ADMIN").build());
  }

  @Test
  public void create() {
    Mockito.when(roleRepository.existsByCode("SUPER_ADMIN"))
        .thenReturn(false);
    service.createRole(RoleDto.builder().authority("SUPER_ADMIN").build());
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateRole_notFound() {
    Mockito.when(roleRepository.findById(1))
        .thenReturn(Optional.empty());
    service.updateRole(RoleDto.builder().id(1).authority("SUPER_ADMIN").build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateRole_duplicate() {
    Role role1 = Role.builder().id(1).code("code").build();
    Role role2 = Role.builder().id(2).code("code2").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code2")).thenReturn(Optional.of(role2));
    service.updateRole(RoleDto.builder().id(1).authority("code2").build());
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void delete() {
    service.deleteRole(1);
    Mockito.verify(roleRepository, Mockito.times(1)).deleteById(1);
  }

  @Test
  public void updateRole_success() {
    Role role1 = Role.builder().id(1).code("code").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code2")).thenReturn(Optional.empty());
    service.updateRole(RoleDto.builder().id(1).authority("code2").build());
    Mockito.verify(roleRepository, Mockito.times(1)).findById(1);
    Mockito.verify(roleRepository, Mockito.times(1)).findByCode("code2");
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void updateRole_success2() {
    Role role1 = Role.builder().id(1).code("code").build();
    Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(role1));
    Mockito.when(roleRepository.findByCode("code")).thenReturn(Optional.of(role1));
    service.updateRole(RoleDto.builder().id(1).authority("code").build());
    Mockito.verify(roleRepository, Mockito.times(1)).findById(1);
    Mockito.verify(roleRepository, Mockito.times(1)).findByCode("code");
    Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void getRoleCodes() {
    service.getRoleCodes();
    Mockito.verify(roleRepository, Mockito.times(1)).findAllCode();
  }

  @Test
  public void getRoleUserListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationBuilderService.from(tableSearchRequest);
    Sort sort = null;
    List<RoleUserListView> result = Arrays.asList(RoleUserListView.builder().build());
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, sort)).thenReturn(result);
    Page<RoleUserListView> roleUserListView = service.getRoleUserListView(tableSearchRequest);
    Assert.assertEquals(1, roleUserListView.getTotalElements());
  }

}
