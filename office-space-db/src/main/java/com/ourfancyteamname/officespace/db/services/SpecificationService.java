package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Path;

@Service
public class SpecificationService {

  public Specification specificationBuilder(TableSearchRequest tableSearchRequest) {
    if (CollectionUtils.isEmpty(tableSearchRequest.getColumnSearchRequests())) {
      return Specification.where(null);
    }
    Specification result = specificationBuilder(tableSearchRequest.getColumnSearchRequests().get(0));
    for (int i = 1; i < tableSearchRequest.getColumnSearchRequests().size(); i++) {
      ColumnSearchRequest rq = tableSearchRequest.getColumnSearchRequests().get(i);
      Specification spec = specificationBuilder(rq);
      Specification previousSpec = Specification.where(result);
      if (previousSpec == null) {
        return Specification.where(spec);
      }
      result = rq.isOrTerm() ? previousSpec.or(spec) : previousSpec.and(spec);
    }
    return result;
  }

  private Specification specificationBuilder(ColumnSearchRequest columnSearchRequest) {
    return (root, query, builder) -> {
      String columnName = columnSearchRequest.getColumnName();
      String term = columnSearchRequest.getTerm();
      if (StringUtils.isBlank(term)) {
        return null;
      }
      Path path = root.get(columnName);
      switch (columnSearchRequest.getOperation()) {
        case EQUAL:
          return builder.equal(path, term);
        case NOT_EQUAL:
          return builder.notEqual(path, term);
        case LIKE:
          return builder.like(path, "%" + term + "%");
        case LESS_THAN:
          return builder.lessThan(path, term);
        case GREATER_THAN:
          return builder.greaterThan(path, term);
        case LESS_THAN_OR_EQUAL_TO:
          return builder.lessThanOrEqualTo(path, term);
        default:
          return builder.greaterThanOrEqualTo(path, term);
      }
    };
  }
}
