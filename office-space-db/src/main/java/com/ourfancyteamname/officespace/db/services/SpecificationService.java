package com.ourfancyteamname.officespace.db.services;

import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SpecificationService {

  public Specification specificationBuilder(TableSearchRequest tableSearchRequest) {
    Specification result = specificationBuilder(tableSearchRequest.getColumnSearchRequests().get(0));
    for (int i = 1; i < tableSearchRequest.getColumnSearchRequests().size(); i++) {
      ColumnSearchRequest rq = tableSearchRequest.getColumnSearchRequests().get(i);
      Specification spec = specificationBuilder(rq);
      result = rq.isOrTerm() ? Specification.where(result).or(spec) : Specification.where(result).and(spec);
    }
    return result;
  }

  private Specification specificationBuilder(ColumnSearchRequest columnSearchRequest) {
    return (root, query, builder) -> {
      String columnName = columnSearchRequest.getColumnName();
      switch (columnSearchRequest.getOperation()) {
        case EQUAL:
          return builder.equal(root.get(columnName), columnSearchRequest.getTerm());
        case NOT_EQUAL:
          return builder.notEqual(root.get(columnName), columnSearchRequest.getTerm());
        case LIKE:
          return builder.like(root.get(columnName), "%" + columnSearchRequest.getTerm() + "%");
        case LESS_THAN:
          return builder.lessThan(root.get(columnName), columnSearchRequest.getTerm());
        case GREATER_THAN:
          return builder.greaterThan(root.get(columnName), columnSearchRequest.getTerm());
        case LESS_THAN_OR_EQUAL_TO:
          return builder.lessThanOrEqualTo(root.get(columnName), columnSearchRequest.getTerm());
        default:
          return builder.greaterThanOrEqualTo(root.get(columnName), columnSearchRequest.getTerm());
      }
    };
  }
}
