package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PermissionCode;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionCodeConverter extends AbstractHibernateEnumConverter<PermissionCode, String> {
  @Override
  protected Class<PermissionCode> getClazz() {
    return PermissionCode.class;
  }
}
