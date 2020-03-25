package com.ourfancyteamname.officespace.postgres.specification.impl;

import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.postgres.entities.User_;
import com.ourfancyteamname.officespace.postgres.specification.SpecificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserSpecificationImpl implements SpecificationService {
  private static final String TABLE_NAME = "User";

  private static final String[] FORBIDDEN = {User_.ID, User_.PASSWORD};

  @Override
  public Specification specificationBuilder(TableSearchRequest tableSearchRequest) {
    if (CollectionUtils.isEmpty(tableSearchRequest.getColumnSearchRequests())) {
      return null;
    }

    Specification result = toSpecification(tableSearchRequest.getColumnSearchRequests().get(0));
    for (int i = 1; i < tableSearchRequest.getColumnSearchRequests().size(); i++) {
      ColumnSearchRequest rq = tableSearchRequest.getColumnSearchRequests().get(i);
      Specification spec = toSpecification(rq);
      result = rq.isOrTerm() ? Specification.where(result).or(spec) : Specification.where(result).and(spec);
    }
    return result;
  }

  private Specification toSpecification(ColumnSearchRequest columnSearchRequest) {
    return SpecificationService.super.specificationBuilder(columnSearchRequest);
  }

  @Override
  public String tableName() {
    return TABLE_NAME;
  }

  @Override
  public String columnName(String input) {
    if (ArrayUtils.contains(FORBIDDEN, input)) {
      throw new UnsupportedOperationException();
    }
    return input;
  }
}
