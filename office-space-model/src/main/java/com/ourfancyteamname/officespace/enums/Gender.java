package com.ourfancyteamname.officespace.enums;

public enum Gender implements PersistableEnum<String> {
  MALE, FEMALE;

  @Override
  public String getName() {
    return this.name();
  }
}
