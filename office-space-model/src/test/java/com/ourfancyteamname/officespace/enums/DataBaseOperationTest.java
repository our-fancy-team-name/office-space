package com.ourfancyteamname.officespace.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class DataBaseOperationTest {

  public static final String[] names =
      {"LESS_THAN", "LESS_THAN_OR_EQUAL_TO", "GREATER_THAN", "GREATER_THAN_OR_EQUAL_TO", "EQUAL", "LIKE", "NOT_EQUAL"};

  @Test
  void name() {
    Stream.of(DataBaseOperation.values())
        .map(Enum::name)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
