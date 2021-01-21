package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaginationBuilderServiceTest {

  private static final int page = 1;
  private static final int pageSize = 10;

  @InjectMocks
  private PaginationBuilderService paginationBuilderService;

  @Test
  void getPage() {
    TablePagingRequest request = TablePagingRequest.builder()
        .page(page)
        .pageSize(pageSize)
        .build();
    Pageable actual = paginationBuilderService.from(request, Sort.unsorted());
    Assertions.assertEquals(Sort.unsorted(), actual.getSort());
    Assertions.assertEquals(page, actual.getPageNumber());
    Assertions.assertEquals(pageSize, actual.getPageSize());
  }

  @Test
  void getPage_empty() {
    TablePagingRequest request = null;
    Pageable actual = paginationBuilderService.from(request, Sort.unsorted());
    Assertions.assertEquals(Sort.unsorted(), actual.getSort());
    Assertions.assertEquals(Pageable.unpaged(), actual);
  }
}
