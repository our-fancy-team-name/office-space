package com.ourfancyteamname.officespace.db.services.impl;


import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.entities.User_;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SpecificationBuilderServiceImplTest {

  @InjectMocks
  private SpecificationBuilderServiceImpl service;

  private CriteriaBuilder criteriaBuilderMock;

  private CriteriaQuery criteriaQueryMock;

  private Root<User> userRootMock;

  private static final String SEARCH_TERM = "foo";

  @BeforeEach
  void setUp() {
    criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
    criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
    userRootMock = Mockito.mock(Root.class);
  }

  @Test
  void specificationBuilder_search_like() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).like(lastNamePathMock, "%" + SEARCH_TERM + "%");
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_equal() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_GreaterThan() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).greaterThan(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_GreaterThanOrEqual() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_lessThan() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).lessThan(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_lessThanOrEqual() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).lessThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_notEqual() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_greaterThanOrEqualTo() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).greaterThanOrEqualTo(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_nullOperator() {
    Path lastNamePathMock = Mockito.mock(Path.class);
    Mockito.when(userRootMock.get(User_.LAST_NAME)).thenReturn(lastNamePathMock);
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
    Assertions.assertThrows(NullPointerException.class,
        () -> actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock));
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
  }

  @Test
  void specificationBuilder_search_unSupport() {
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
        .columnSearchRequests(Collections.singletonList(columnSearchRequest))
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Predicate actualPredicate = actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).notEqual(lastNamePathMock, SEARCH_TERM);
    Assertions.assertEquals(lastNameIsLikePredicateMock, actualPredicate);
  }

  @Test
  void specificationBuilder_search_multiple_and() {
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
    Specification<User> actual = service.from(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.FIRST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }


  @Test
  void specificationBuilder_search_multiple_or() {
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
    Specification<User> actual = service.from(tableSearchRequest);
    actual.toPredicate(userRootMock, criteriaQueryMock, criteriaBuilderMock);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.LAST_NAME);
    Mockito.verify(userRootMock, Mockito.times(1)).get(User_.FIRST_NAME);
    Mockito.verifyNoMoreInteractions(userRootMock);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(lastNamePathMock, SEARCH_TERM);
    Mockito.verify(criteriaBuilderMock, Mockito.times(1)).equal(firstNamePathMock, SEARCH_TERM);
  }

  @Test
  void specificationBuilder_search_with_null_input() {
    TableSearchRequest tableSearchRequest = TableSearchRequest
        .builder()
        .columnSearchRequests(null)
        .build();
    Specification<User> actual = service.from(tableSearchRequest);
    Assertions.assertEquals(Specification.where(null), actual);
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
    Assertions.assertNull(actualPredicate);
  }

  /**
   * for @{@link org.springframework.beans.factory.annotation.Qualifier}
   */
  @Test
  void qualifier() {
    Assertions.assertEquals("SpecificationBuilderServiceImpl", service.getClass().getSimpleName());
  }
}
