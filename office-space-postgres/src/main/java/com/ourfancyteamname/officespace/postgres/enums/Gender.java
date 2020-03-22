package com.ourfancyteamname.officespace.postgres.enums;

import com.ourfancyteamname.officespace.postgres.converters.AbstractHibernateEnumConverter;

public enum Gender implements PersistableEnum<String> {
  MALE, FEMALE;

  @Override
  public String getName() {
    return this.name();
  }

  public static class HibernateConverter extends AbstractHibernateEnumConverter<Gender, String> {
    public HibernateConverter() {
      super(Gender.class);
    }
  }
}
