package com.ourfancyteamname.officespace.postgres.specification.impl;

import com.ourfancyteamname.officespace.postgres.entities.User_;
import com.ourfancyteamname.officespace.postgres.specification.SpecificationService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

@Service
public class UserSpecificationImpl implements SpecificationService {
  private static final String TABLE_NAME = "User";

  private static final String[] FORBIDDEN = {User_.ID, User_.PASSWORD};

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
