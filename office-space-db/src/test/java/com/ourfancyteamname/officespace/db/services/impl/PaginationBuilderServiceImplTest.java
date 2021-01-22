package com.ourfancyteamname.officespace.db.services.impl;

import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
    Mockito.when(sortingBuilderService.from(tableSearchRq)).thenReturn(Sort.unsorted());
    Pageable actual = service.from(tableSearchRq);
    Assertions.assertEquals(Sort.unsorted(), actual.getSort());
    Assertions.assertEquals(page, actual.getPageNumber());
    Assertions.assertEquals(pageSize, actual.getPageSize());
  }

  @Test
  void getPage_empty() {
    var tableSearchRq = TableSearchRequest.builder().build();
    Pageable actual = service.from(tableSearchRq);
    Assertions.assertEquals(Sort.unsorted(), actual.getSort());
    Assertions.assertEquals(Pageable.unpaged(), actual);
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier}
   */
  @Test
  void qualifier() {
    Assertions.assertEquals("PaginationBuilderServiceImpl", service.getClass().getSimpleName());
  }
}
