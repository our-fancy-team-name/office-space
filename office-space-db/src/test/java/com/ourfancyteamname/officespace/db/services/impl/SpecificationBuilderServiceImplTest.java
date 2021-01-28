package com.ourfancyteamname.officespace.db.services.impl;


import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.jpa.domain.Specification;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.User_;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;


@UnitTest
class SpecificationBuilderServiceImplTest {

  @InjectMocks
  private SpecificationBuilderServiceImpl service;

  private CriteriaBuilder criteriaBuilderMock;

  private CriteriaQuery criteriaQueryMock;

  private Root<User> userRootMock;

  private static final String SEARCH_TERM = "foo";

  @BeforeEach
  void setUp() {
    criteriaBuilderMock = mock(CriteriaBuilder.class);
    criteriaQueryMock = mock(CriteriaQuery.class);
    userRootMock = mock(Root.class);
  }

  @Test
  void specificationBuilder_search_like() {
    Path lastNamePathMock = mock(Path.class);
    mockReturn(userRootMock.get(User_.LAST_NAME), lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    mockReturn(criteriaBuilderMock.like(lastNamePathMock, "%" + SEARCH_TERM + "%"), lastNameIsLikePredicateMock);

    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LIKE)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verifyInvoke1Time(userRootMock).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verifyInvoke1Time(criteriaBuilderMock).like(lastNamePathMock, "%" + SEARCH_TERM + "%");
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_equal() {
    Path lastNamePathMock = mock(Path.class);
    mockReturn(userRootMock.get(User_.LAST_NAME), lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    mockReturn(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM), lastNameIsLikePredicateMock);

    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verifyInvoke1Time(userRootMock).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verifyInvoke1Time(criteriaBuilderMock).equal(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_GreaterThan() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.greaterThan(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.GREATER_THAN)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).greaterThan(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_GreaterThanOrEqual() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.GREATER_THAN_OR_EQUAL_TO)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_lessThan() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.lessThan(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LESS_THAN)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).lessThan(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_lessThanOrEqual() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.lessThanOrEqualTo(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.LESS_THAN_OR_EQUAL_TO)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).lessThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_notEqual() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.notEqual(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.NOT_EQUAL)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_greaterThanOrEqualTo() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.GREATER_THAN_OR_EQUAL_TO)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_nullOperator() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(null)
        .term(SEARCH_TERM)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    assertThrows(NullPointerException.class,
        () -> actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock));
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
  }

  @Test
  void specificationBuilder_search_unSupport() {
    Path lastNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.notEqual(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    ColumnSearchRequest columnSearchRequest = ColumnSearchRequest.builder()
        .columnName(User_.LAST_NAME)
        .operation(DataBaseOperation.NOT_EQUAL)
        .term(SEARCH_TERM)
        .isOrTerm(true)
        .build();
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_multiple_and() {
    Path lastNamePathMock = mock(Path.class);
    Path firstNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    when(userRootMock.get(User_.FIRST_NAME)).thenReturn(firstNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    Predicate firstNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    when(criteriaBuilderMock.equal(firstNamePathMock, SEARCH_TERM))
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
    Specification<User> actual = service.from(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verify(userRootMock, times(1)).get(User_.FIRST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).equal(lastNamePathMock, SEARCH_TERM);
    verify(criteriaBuilderMock, times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }


  @Test
  void specificationBuilder_search_multiple_or() {
    Path lastNamePathMock = mock(Path.class);
    Path firstNamePathMock = mock(Path.class);
    when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
    when(userRootMock.get(User_.FIRST_NAME)).thenReturn(firstNamePathMock);
    Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
    Predicate firstNameIsLikePredicateMock = mock(Predicate.class);
    when(criteriaBuilderMock.equal(lastNamePathMock, SEARCH_TERM))
        .thenReturn(lastNameIsLikePredicateMock);
    when(criteriaBuilderMock.equal(firstNamePathMock, SEARCH_TERM))
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
    Specification<User> actual = service.from(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    verify(userRootMock, times(1)).get(User_.LAST_NAME);
    verify(userRootMock, times(1)).get(User_.FIRST_NAME);
    verifyNoMoreInteractions(userRootMock);
    verify(criteriaBuilderMock, times(1)).equal(lastNamePathMock, SEARCH_TERM);
    verify(criteriaBuilderMock, times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }

  @Test
  void specificationBuilder_search_with_null_input() {
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(null)
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    assertEquals(Specification.where(null), actual);
  }

  @Test
  void specificationBuilder_search_with_empty_search_term() {
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
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    assertNull(actualPredicate);
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier}
   */
  @Test
  void qualifier() {
    assertEquals("SpecificationBuilderServiceImpl", service.getClass().getSimpleName());
  }
}
