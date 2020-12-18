package com.ourfancyteamname.officespace.enums;

public enum PermissionCode implements PersistableEnum<String> {
  USER_EDIT, ROLE_EDIT, PRD_EDIT, PKG_EDIT, CLUS_EDIT, NODE_EDIT;

  @Override
  public String getName() {
    return this.name();
  }
}
