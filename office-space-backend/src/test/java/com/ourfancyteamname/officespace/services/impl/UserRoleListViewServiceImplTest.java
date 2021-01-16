package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
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

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
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
    Assert.assertEquals(userRoleListViewRepository, service.getExecutor());
  }

  @Test
  public void findAll() {
    List<UserRoleListView> result = Arrays.asList(UserRoleListView.builder().build());
    Specification specs = null;
    Sort sort = null;
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, sort)).thenReturn(result);
    Page<UserRoleListView> actual = service.findAll(TableSearchRequest.builder().build());
    Assert.assertEquals(1, actual.getTotalElements());
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier} on {@link UserServiceImpl}
   */
  @Test
  public void qualifier() {
    Assert.assertEquals("UserRoleListViewServiceImpl", service.getClass().getSimpleName());
  }

}