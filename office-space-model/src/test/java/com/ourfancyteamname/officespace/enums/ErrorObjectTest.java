package com.ourfancyteamname.officespace.enums;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class ErrorObjectTest {
  public static final String[] names = {"NAME", "PART_NUMBER", "CLUSTER", "SERIAL"};

  @Test
  void name() {
    Stream.of(ErrorObject.values())
        .map(Enum::name)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
