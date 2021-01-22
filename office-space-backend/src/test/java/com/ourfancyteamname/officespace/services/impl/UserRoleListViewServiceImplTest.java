package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
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

  @Mock(name = "specificationBuilderService")
  private SpecificationBuilderServiceImpl<UserRole> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderService")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderService")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Test
  void getExecutor() {
    Assertions.assertEquals(userRoleListViewRepository, service.getExecutor());
  }

  @Test
  void findAll() {
    var result = Collections.singletonList(UserRoleListView.builder().build());
    Mockito.when(paginationBuilderServiceImpl.from(TableSearchRequest.builder().build()))
        .thenReturn(Pageable.unpaged());
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