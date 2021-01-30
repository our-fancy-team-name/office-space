package com.ourfancyteamname.officespace.test.services;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class AssertionHelperTest {

  private static final Executable executable = () -> {
    throw new IllegalArgumentException();
  };

  @Test
  void assertThrowIllegal() {
    AssertionHelper.assertThrowIllegal(executable);
  }

  @Test
  void assertThrowIllegalMessage() {
    AssertionHelper.assertThrowIllegal(executable, "message");
  }

  @Test
  void assertThrowIllegalObject() {
    AssertionHelper.assertThrowIllegal(executable, "errorObject", "errorCode");
  }

  @Test
  void assertThrowIllegalDuplicated() {
    AssertionHelper.assertThrowIllegalDuplicated(executable);
  }

  @Test
  void assertThrowIllegalNotFound() {
    AssertionHelper.assertThrowIllegalNotFound(executable);
  }

  @Test
  void assertThrowIllegalInUse() {
    AssertionHelper.assertThrowIllegalInUse(executable);
  }

  @Test
  void assertObjectInUse() {
    AssertionHelper.assertObjectInUse(executable, "Object");
  }

  @Test
  void assertObjectDuplicated() {
    AssertionHelper.assertObjectDuplicated(executable, "Object");
  }

  @Test
  void assertCollectionEquals() {
    List<Integer> actual = Arrays.asList(1, 1, 1, 3, 5, 6, 0);
    List<Integer> expect = Arrays.asList(1, 1, 3, 1, 5, 6, 0);
    AssertionHelper.assertCollectionEquals(expect, actual);
  }

}
