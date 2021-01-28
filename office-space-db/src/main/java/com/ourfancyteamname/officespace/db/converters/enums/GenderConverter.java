package com.ourfancyteamname.officespace.db.converters.enums;

import javax.persistence.Converter;

import com.ourfancyteamname.officespace.enums.Gender;

@Converter(autoApply = true)
public class GenderConverter extends AbstractHibernateEnumConverter<Gender, String> {

  @Override
  protected Class<Gender> getClazz() {
    return Gender.class;
  }
}
