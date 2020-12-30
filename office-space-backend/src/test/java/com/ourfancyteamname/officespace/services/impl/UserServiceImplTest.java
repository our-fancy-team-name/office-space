package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.UserConverter;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.User_;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSortingRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final String SEARCH_TERM = "foo";

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private SpecificationService specificationService;

  @Mock
  private SortingService sortingService;

  @Mock
  private PaginationService paginationService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserConverter userConverter;

  @Test
  public void findAllByPaging() {
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSortingRequest tableSortingRequest = TableSortingRequest.builder()
        .columnName(User_.LAST_NAME)
        .direction(DataBaseDirection.ASC)
        .build();
    TablePagingRequest tablePagingRequest = TablePagingRequest.builder()
        .page(0)
        .pageSize(10)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .pagingRequest(tablePagingRequest)
        .sortingRequest(tableSortingRequest)
        .build();
    User aUser = User.builder()
        .id(1)
        .email("dang")
        .build();
    Specification<User> specs = (root, query, builder) -> builder.equal(root.get(User_.LAST_NAME), SEARCH_TERM);
    Mockito.when(specificationService.specificationBuilder(tableSearchRequest)).thenReturn(specs);
    Mockito.when(sortingService.getSort(tableSortingRequest)).thenReturn(Sort.unsorted());
    Mockito.when(paginationService.getPage(tablePagingRequest, Sort.unsorted())).thenReturn(PageRequest.of(0, 10));
    Mockito.when(userRepository.findAll(specs, PageRequest.of(0, 10)))
        .thenReturn(new PageImpl(Arrays.asList(aUser), PageRequest.of(0, 10), 1));
    Mockito.when(userConverter.toDto(aUser))
        .thenReturn(UserDto.builder().email("dang").build());
    Page<UserDto> actual = userService.findAllByPaging(tableSearchRequest);
    Assert.assertEquals(1, actual.getContent().size());
    Assert.assertEquals("dang", actual.getContent().get(0).getEmail());
  }
}
