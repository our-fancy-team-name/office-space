package com.ourfancyteamname.officespace.db.converters.enums;

import javax.persistence.Converter;

import com.ourfancyteamname.officespace.enums.PackageStatus;

@Converter(autoApply = true)
public class PackageStatusConverter extends AbstractHibernateEnumConverter<PackageStatus, String> {

  @Override
  protected Class<PackageStatus> getClazz() {
    return PackageStatus.class;
  }
}
