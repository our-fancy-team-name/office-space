package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SortingBuilderServiceTest {

  private static String columnName = "dang";

  @InjectMocks
  private SortingBuilderService sortingBuilderService;

  @Test
  void getSort_asc() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.ASC)
        .build();
    Sort actual = sortingBuilderService.from(request);
    Assertions.assertEquals(Sort.Direction.ASC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  void getSort_desc() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.DESC)
        .build();
    Sort actual = sortingBuilderService.from(request);
    Assertions.assertEquals(Sort.Direction.DESC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  void getSort_unSort1() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(StringUtils.EMPTY)
        .direction(DataBaseDirection.DESC)
        .build();
    Sort actual = sortingBuilderService.from(request);
    Assertions.assertEquals(Sort.unsorted(), actual);
  }

  @Test
  void getSort_unSort2() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(null)
        .build();
    Sort actual = sortingBuilderService.from(request);
    Assertions.assertEquals(Sort.unsorted(), actual);
  }

  @Test
  void getSort_unSort3() {
    TableSortingRequest request = null;
    Sort actual = sortingBuilderService.from(request);
    Assertions.assertEquals(Sort.unsorted(), actual);
  }
}
