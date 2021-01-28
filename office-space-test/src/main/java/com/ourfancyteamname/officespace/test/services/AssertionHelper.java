package com.ourfancyteamname.officespace.test.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AssertionHelper {

  private static final String DELIMITER = ":";
  private static final String DUPLICATED = "DUPLICATED";
  private static final String NOT_FOUND = "NOT_FOUND";
  private static final String IN_USE = "IN_USE";

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
