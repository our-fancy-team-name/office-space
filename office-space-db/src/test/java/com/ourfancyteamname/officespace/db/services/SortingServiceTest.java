package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SortingServiceTest {

  private static String columnName = "dang";

  @InjectMocks
  private SortingService sortingService;

  @Test
  public void getSort_asc() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.ASC)
        .build();
    Sort actual = sortingService.getSort(request);
    Assert.assertEquals(Sort.Direction.ASC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  public void getSort_desc() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(DataBaseDirection.DESC)
        .build();
    Sort actual = sortingService.getSort(request);
    Assert.assertEquals(Sort.Direction.DESC, actual.getOrderFor(columnName).getDirection());
  }

  @Test
  public void getSort_unSort1() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(StringUtils.EMPTY)
        .direction(DataBaseDirection.DESC)
        .build();
    Sort actual = sortingService.getSort(request);
    Assert.assertEquals(Sort.unsorted(), actual);
  }

  @Test
  public void getSort_unSort2() {
    TableSortingRequest request = TableSortingRequest.builder()
        .columnName(columnName)
        .direction(null)
        .build();
    Sort actual = sortingService.getSort(request);
    Assert.assertEquals(Sort.unsorted(), actual);
  }

  @Test
  public void getSort_unSort3() {
    TableSortingRequest request = null;
    Sort actual = sortingService.getSort(request);
    Assert.assertEquals(Sort.unsorted(), actual);
  }
}
