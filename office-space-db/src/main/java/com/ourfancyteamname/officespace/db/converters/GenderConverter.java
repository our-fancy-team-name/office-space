package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.enums.Gender;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter extends AbstractHibernateEnumConverter<Gender, String> {
  public GenderConverter() {
    super(Gender.class);
  }
}
