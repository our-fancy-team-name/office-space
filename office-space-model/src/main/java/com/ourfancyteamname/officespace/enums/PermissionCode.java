package com.ourfancyteamname.officespace.enums;

public enum PermissionCode implements PersistableEnum<String> {
  USER_EDIT, USER_DELETE, ROLE_EDIT, ROLE_DELETE;

  @Override
  public String getName() {
    return this.name();
  }
}
