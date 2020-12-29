package com.ourfancyteamname.officespace.db.services;


import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.User_;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SpecificationServiceTest {

  @InjectMocks
  private SpecificationService specificationService;

  private CriteriaBuilder criteriaBuilderMock;

  private CriteriaQuery criteriaQueryMock;

  private Root<User> userRootMock;

  private static final String SEARCH_TERM = "foo";

  @Before
  public void setUp() {
    criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
    criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
    userRootMock = Mockito.mock(Root.class);
  }

  @Test
  public void specificationBuilder_search_like() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.like(lastNamePathMock, "%" + SEARCH_TERM + "%"))
        .thenReturn(lastNameIsLikePredicateMock);

    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LIKE)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).like(lastNamePathMock, "%" + SEARCH_TERM + "%");
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_equal() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);

    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_GreaterThan() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.greaterThan(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.GREATER_THAN)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).greaterThan(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_GreaterThanOrEqual() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.GREATER_THAN_OR_EQUAL_TO)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_lessThan() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.lessThan(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LESS_THAN)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).lessThan(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_lessThanOrEqual() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.lessThanOrEqualTo(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LESS_THAN_OR_EQUAL_TO)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).lessThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_notEqual() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.notEqual(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.NOT_EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_unSupport() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.notEqual(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.NOT_EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(true)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    Assert.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  public void specificationBuilder_search_multiple_and() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Path firstNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Mockito.when(userRootMock.get(User_.FIRST_NAME)).thenReturn(firstNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Predicate firstNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    Mockito.when(criteriaBuilderMock.equal(firstNamePathMock, SEARCH_TERM))
        .thenReturn(firstNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(true)
        .build();
    ColumnSearchRequest columnSearchRequest2 = ColumnSearchRequest.builder()
        .columnName(User_.FIRST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(true)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest, columnSearchRequest2))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.FIRST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }


  @Test
  public void specificationBuilder_search_multiple_or() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Path firstNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Mockito.when(userRootMock.get(User_.FIRST_NAME)).thenReturn(firstNamePathMock);
    Predicate lastNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Predicate firstNameIsLikePredicateMock = Mockito.mock(Predicate.class);
    Mockito.when(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    Mockito.when(criteriaBuilderMock.equal(firstNamePathMock, SEARCH_TERM))
        .thenReturn(firstNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(true)
        .build();
    ColumnSearchRequest columnSearchRequest2 = ColumnSearchRequest.builder()
        .columnName(User_.FIRST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(false)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest, columnSearchRequest2))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.FIRST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }

  @Test
  public void specificationBuilder_search_with_null_input() {
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(null)
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Assert.assertEquals(Specification.where(null), actual);
  }

  @Test
  public void specificationBuilder_search_with_empty_search_term() {
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term("")
        .isOrTerm(true)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Arrays.asList(columnSearchRequest, columnSearchRequest))
        .build();
    Specification<User> actual = specificationService.specificationBuilder(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Assert.assertEquals(null, actualPredicate);
  }
}
