package com.ourfancyteamname.officespace.enums;

public enum ClusterNodePosition implements PersistableEnum<String> {
  TAIL, HEAD, BODY;

  @Override
  public String getName() {
    return this.name();
  }
}
