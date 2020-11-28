package com.ourfancyteamname.officespace.enums;

public enum RoleEnum implements PersistableEnum<String> {
  SUPER_ADMIN;

  @Override
  public String getName() {
    return this.name();
  }
}
