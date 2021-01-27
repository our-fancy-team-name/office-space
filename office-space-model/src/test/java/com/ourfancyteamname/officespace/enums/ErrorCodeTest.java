package com.ourfancyteamname.officespace.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class ErrorCodeTest {
  public static final String[] names = {"DUPLICATED", "NOT_FOUND", "IN_USE"};

  @Test
  void name() {
    Stream.of(ErrorCode.values())
        .map(Enum::name)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
