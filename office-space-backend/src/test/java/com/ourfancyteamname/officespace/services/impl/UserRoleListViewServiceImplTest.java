package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class UserRoleListViewServiceImplTest {

  @InjectMocks
  private UserRoleListViewServiceImpl service;

  @Mock
  private UserRoleListViewRepository userRoleListViewRepository;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<UserRole> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Test
  void getExecutor() {
    assertEquals(userRoleListViewRepository, service.getExecutor());
  }

  @Test
  void findAll() {
    var result = Collections.singletonList(UserRoleListView.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(TableSearchRequest.builder().build()), Pageable.unpaged());
    mockReturn(service.getExecutor().findAll((Specification<UserRoleListView>) null, (Sort) null), result);
    Page<UserRoleListView> actual = service.findAll(TableSearchRequest.builder().build());
    assertEquals(1, actual.getTotalElements());
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier} on {@link UserServiceImpl}
   */
  @Test
  void qualifier() {
    assertEquals("UserRoleListViewServiceImpl", service.getClass().getSimpleName());
  }

}