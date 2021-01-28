package com.ourfancyteamname.officespace.db.services.impl;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PaginationBuilderServiceImplTest {

  private static final int page = 1;
  private static final int pageSize = 10;

  @InjectMocks
  private PaginationBuilderServiceImpl service;

  @Mock
  private SortingBuilderServiceImpl sortingBuilderService;

  @Test
  void getPage() {
    var request = TablePagingRequest.builder()
        .page(page)
        .pageSize(pageSize)
        .build();
    var tableSearchRq = TableSearchRequest.builder().pagingRequest(request).build();
    mockReturn(sortingBuilderService.from(tableSearchRq), Sort.unsorted());
    Pageable actual = service.from(tableSearchRq);
    assertEquals(Sort.unsorted(), actual.getSort());
    assertEquals(page, actual.getPageNumber());
    assertEquals(pageSize, actual.getPageSize());
  }

  @Test
  void getPage_empty() {
    var tableSearchRq = TableSearchRequest.builder().build();
    Pageable actual = service.from(tableSearchRq);
    assertEquals(Sort.unsorted(), actual.getSort());
    assertEquals(Pageable.unpaged(), actual);
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier}
   */
  @Test
  void qualifier() {
    assertEquals("PaginationBuilderServiceImpl", service.getClass().getSimpleName());
  }
}
