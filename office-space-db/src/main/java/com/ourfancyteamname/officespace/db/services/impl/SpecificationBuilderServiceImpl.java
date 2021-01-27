package com.ourfancyteamname.officespace.db.services.impl;

import com.ourfancyteamname.officespace.db.services.TableSearchBuilderService;
import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Path;

@Service
public class SpecificationBuilderServiceImpl<T> implements TableSearchBuilderService<Specification<T>> {

  public Specification<T> from(TableSearchRequest tableSearchRequest) {
    if (CollectionUtils.isEmpty(tableSearchRequest.getColumnSearchRequests())) {
      return Specification.where(null);
    }
    var result = this.from(tableSearchRequest.getColumnSearchRequests().get(0));
    for (int i = 1; i < tableSearchRequest.getColumnSearchRequests().size(); i++) {
      final var previousSpec =
          ObjectUtils.defaultIfNull(Specification.where(result), Specification.where((Specification<T>) null));
      final var rq = tableSearchRequest.getColumnSearchRequests().get(i);
      final var spec = from(rq);
      result = rq.isOrTerm() ? previousSpec.or(spec) : previousSpec.and(spec);
    }
    return result;
  }

  private Specification<T> from(ColumnSearchRequest columnSearchRequest) {
    return (root, query, builder) -> {
      final var term = columnSearchRequest.getTerm();
      if (StringUtils.isBlank(term)) {
        return null;
      }
      final Path<String> path = root.get(columnSearchRequest.getColumnName());
      return switch (columnSearchRequest.getOperation()) {
        case EQUAL -> builder.equal(path, term);
        case NOT_EQUAL -> builder.notEqual(path, term);
        case LIKE -> builder.like(path, "%" + term + "%");
        case LESS_THAN -> builder.lessThan(path, term);
        case GREATER_THAN -> builder.greaterThan(path, term);
        case LESS_THAN_OR_EQUAL_TO -> builder.lessThanOrEqualTo(path, term);
        default -> builder.greaterThanOrEqualTo(path, term);
      };
    };
  }
}
