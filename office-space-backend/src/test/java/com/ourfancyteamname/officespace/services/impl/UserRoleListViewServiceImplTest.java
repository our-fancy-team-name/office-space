package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserRoleListViewServiceImplTest {

  @InjectMocks
  private UserRoleListViewServiceImpl service;

  @Mock
  private UserRoleListViewRepository userRoleListViewRepository;

  @Mock
  private PaginationBuilderService paginationBuilderService;

  @Mock
  private SpecificationBuilderService<UserRole> specificationBuilderService;

  @Mock
  private SortingBuilderService sortingBuilderService;

  @Test
  void getExecutor() {
    Assertions.assertEquals(userRoleListViewRepository, service.getExecutor());
  }

  @Test
  void findAll() {
    var result = Collections.singletonList(UserRoleListView.builder().build());
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll((Specification<UserRoleListView>) null, (Sort) null)).thenReturn(result);
    Page<UserRoleListView> actual = service.findAll(TableSearchRequest.builder().build());
    Assertions.assertEquals(1, actual.getTotalElements());
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier} on {@link UserServiceImpl}
   */
  @Test
  void qualifier() {
    Assertions.assertEquals("UserRoleListViewServiceImpl", service.getClass().getSimpleName());
  }

}