package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PackageStatusConverter extends AbstractHibernateEnumConverter<PackageStatus, String> {

  @Override
  protected Class<PackageStatus> getClazz() {
    return PackageStatus.class;
  }
}
