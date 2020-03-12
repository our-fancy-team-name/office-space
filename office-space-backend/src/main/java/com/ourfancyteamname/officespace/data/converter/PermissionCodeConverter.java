package com.ourfancyteamname.officespace.data.converter;

import com.ourfancyteamname.officespace.data.enums.PermissionCode;
import org.apache.commons.lang3.EnumUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;
import java.util.function.Supplier;

@Converter
public class PermissionCodeConverter implements AttributeConverter<PermissionCode, String> {

  private Supplier<IllegalArgumentException> throwError(String dbData) {
    return () -> new IllegalArgumentException(String.format("Permission %s cannot convert to PermissionCode", dbData));
  }

  @Override
  public String convertToDatabaseColumn(PermissionCode attribute) {
    return attribute == null ? null : attribute.name();
  }

  @Override
  public PermissionCode convertToEntityAttribute(String dbData) {
    return Optional.ofNullable(EnumUtils.getEnum(PermissionCode.class, dbData))
        .orElseThrow(throwError(dbData));
  }
}
