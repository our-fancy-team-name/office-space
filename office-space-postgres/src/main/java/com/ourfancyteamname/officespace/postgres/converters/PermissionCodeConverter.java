package com.ourfancyteamname.officespace.postgres.converters;

import com.ourfancyteamname.officespace.enums.PermissionCode;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionCodeConverter extends AbstractHibernateEnumConverter<PermissionCode, String> {
  public PermissionCodeConverter() {
    super(PermissionCode.class);
  }
}
