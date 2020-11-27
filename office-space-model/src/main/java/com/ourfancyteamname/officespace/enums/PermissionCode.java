package com.ourfancyteamname.officespace.enums;

public enum PermissionCode implements PersistableEnum<String> {
  DELETE_USER;

  @Override
  public String getName() {
    return this.name();
  }
}
