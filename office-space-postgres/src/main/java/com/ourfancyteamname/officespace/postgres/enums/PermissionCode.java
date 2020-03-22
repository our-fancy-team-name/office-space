package com.ourfancyteamname.officespace.postgres.enums;

import com.ourfancyteamname.officespace.postgres.converters.AbstractHibernateEnumConverter;

public enum PermissionCode implements PersistableEnum<String> {
  DELETE_USER;

  @Override
  public String getName() {
    return this.name();
  }

  public static class HibernateConverter extends AbstractHibernateEnumConverter<PermissionCode, String> {
    public HibernateConverter() {
      super(PermissionCode.class);
    }
  }
}
