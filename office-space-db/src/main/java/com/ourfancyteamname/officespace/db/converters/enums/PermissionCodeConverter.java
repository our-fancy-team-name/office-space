package com.ourfancyteamname.officespace.db.converters.enums;

import javax.persistence.Converter;

import com.ourfancyteamname.officespace.enums.PermissionCode;

@Converter(autoApply = true)
public class PermissionCodeConverter extends AbstractHibernateEnumConverter<PermissionCode, String> {
  @Override
  protected Class<PermissionCode> getClazz() {
    return PermissionCode.class;
  }
}
