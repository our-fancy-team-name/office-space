package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PaginationServiceTest {

  private static final int page = 1;
  private static final int pageSize = 10;

  @InjectMocks
  private PaginationService paginationService;

  @Test
  public void getPage() {
    TablePagingRequest request = TablePagingRequest.builder()
        .page(page)
        .pageSize(pageSize)
        .build();
    Pageable actual = paginationService.getPage(request, Sort.unsorted());
    Assert.assertEquals(Sort.unsorted(), actual.getSort());
    Assert.assertEquals(page, actual.getPageNumber());
    Assert.assertEquals(pageSize, actual.getPageSize());
  }

  @Test
  public void getPage_empty() {
    TablePagingRequest request = null;
    Pageable actual = paginationService.getPage(request, Sort.unsorted());
    Assert.assertEquals(Sort.unsorted(), actual.getSort());
    Assert.assertEquals(Pageable.unpaged(), actual);
  }
}
