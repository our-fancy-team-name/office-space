package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.RoleEnum;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleEnumConverter extends AbstractHibernateEnumConverter<RoleEnum, String> {
  public RoleEnumConverter() {
    super(RoleEnum.class);
  }
}
