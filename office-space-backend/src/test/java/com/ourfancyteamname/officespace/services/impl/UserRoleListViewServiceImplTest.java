package com.ourfancyteamname.officespace.services.impl;

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

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserRoleListViewServiceImplTest {

  @InjectMocks
  private UserRoleListViewServiceImpl service;

  @Mock
  private UserRoleListViewRepository userRoleListViewRepository;

  @Mock
  private PaginationBuilderService paginationBuilderService;

  @Mock
  private SpecificationBuilderService specificationBuilderService;

  @Mock
  private SortingBuilderService sortingBuilderService;

  @Test
  public void getExecutor() {
    Assertions.assertEquals(userRoleListViewRepository, service.getExecutor());
  }

  @Test
  public void findAll() {
    List<UserRoleListView> result = Arrays.asList(UserRoleListView.builder().build());
    Specification specs = null;
    Sort sort = null;
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, sort)).thenReturn(result);
    Page<UserRoleListView> actual = service.findAll(TableSearchRequest.builder().build());
    Assertions.assertEquals(1, actual.getTotalElements());
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier} on {@link UserServiceImpl}
   */
  @Test
  public void qualifier() {
    Assertions.assertEquals("UserRoleListViewServiceImpl", service.getClass().getSimpleName());
  }

}