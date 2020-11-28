package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.db.converters.enums.AbstractHibernateEnumConverter;
import com.ourfancyteamname.officespace.enums.PermissionCode;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionCodeConverter extends AbstractHibernateEnumConverter<PermissionCode, String> {
  public PermissionCodeConverter() {
    super(PermissionCode.class);
  }
}
