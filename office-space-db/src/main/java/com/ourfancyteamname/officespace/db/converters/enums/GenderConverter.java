package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.Gender;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter extends AbstractHibernateEnumConverter<Gender, String> {

  @Override
  protected Class<Gender> getClazz() {
    return Gender.class;
  }
}
