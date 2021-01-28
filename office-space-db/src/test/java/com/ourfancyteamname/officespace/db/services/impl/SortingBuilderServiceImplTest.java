package com.ourfancyteamname.officespace.db.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Sort;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class SortingBuilderServiceImplTest {

  private static String columnName = "dang";

  @InjectMocks
  private SortingBuilderServiceImpl service;

  @Test
  void getSort_asc() {
    var request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.ASC)
        .build();
    var tableSearchRequest = TableSearchRequest.builder().sortingRequest(request).build();
    var actual = service.from(tableSearchRequest);
    assertEquals(Sort.Direction.ASC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  void getSort_desc() {
    var request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.DESC)
        .build();
    var tableSearchRequest = TableSearchRequest.builder().sortingRequest(request).build();
    var actual = service.from(tableSearchRequest);
    assertEquals(Sort.Direction.DESC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  void getSort_unSort1() {
    var request = TableSortingRequest.builder()
        .columnName(StringUtils.EMPTY)
        .direction(DataBaseDirection.DESC)
        .build();
    var tableSearchRequest = TableSearchRequest.builder().sortingRequest(request).build();
    var actual = service.from(tableSearchRequest);
    assertEquals(Sort.unsorted(), actual);
  }

  @Test
  void getSort_unSort2() {
    var request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(null)
        .build();
    var tableSearchRequest = TableSearchRequest.builder().sortingRequest(request).build();
    var actual = service.from(tableSearchRequest);
    assertEquals(Sort.unsorted(), actual);
  }

  @Test
  void getSort_unSort3() {
    var tableSearchRequest = TableSearchRequest.builder().build();
    var actual = service.from(tableSearchRequest);
    assertEquals(Sort.unsorted(), actual);
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier}
   */
  @Test
  void qualifier() {
    assertEquals("SortingBuilderServiceImpl", service.getClass().getSimpleName());
  }
}
