package com.ourfancyteamname.officespace.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class DataBaseDirectionTest {
  public static final String[] names = {"ASC", "DESC"};

  @Test
  void name() {
    Stream.of(DataBaseDirection.values())
        .map(Enum::name)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
