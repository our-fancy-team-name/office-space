package com.ourfancyteamname.officespace.enums;

public enum PackageStatus implements PersistableEnum<String> {
  FAIL, PASS, WIP;

  @Override
  public String getName() {
    return this.name();
  }
}
