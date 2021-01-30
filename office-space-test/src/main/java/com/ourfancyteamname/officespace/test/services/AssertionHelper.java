package com.ourfancyteamname.officespace.test.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AssertionHelper {

  private static final String DELIMITER = ":";
  private static final String DUPLICATED = "DUPLICATED";
  private static final String NOT_FOUND = "NOT_FOUND";
  private static final String IN_USE = "IN_USE";

  public <T, A extends Collection<T>> void assertCollectionEquals(A expect, A actual) {
    assertArrayEquals(expect.toArray(), actual.toArray());
  }

  public <A> void assertArrayEquals(A[] expect, A[] actual) {
    A[] expectClone = Arrays.copyOf(expect, expect.length);
    A[] actualClone = Arrays.copyOf(actual, actual.length);
    Arrays.sort(expectClone);
    Arrays.sort(actualClone);
    Assertions.assertArrayEquals(expectClone, actualClone);
  }

  public void assertThrowIllegal(Executable run) {
    assertThrows(IllegalArgumentException.class, run);
  }

  public void assertThrowIllegal(Executable run, String message) {
    assertThrows(IllegalArgumentException.class, run, message);
  }

  public void assertThrowIllegal(Executable run, String errorObject, String errorCode) {
    assertThrowIllegal(run, String.join(DELIMITER, errorObject, errorCode));
  }

  public void assertThrowIllegalDuplicated(Executable run) {
    assertThrowIllegal(run, DUPLICATED);
  }

  public void assertThrowIllegalNotFound(Executable run) {
    assertThrowIllegal(run, NOT_FOUND);
  }

  public void assertThrowIllegalInUse(Executable run) {
    assertThrowIllegal(run, IN_USE);
  }

  public void assertObjectInUse(Executable run, String object) {
    assertThrowIllegal(run, String.join(DELIMITER, object, IN_USE));
  }

  public void assertObjectDuplicated(Executable run, String object) {
    assertThrowIllegal(run, String.join(DELIMITER, object, DUPLICATED));
  }

}
