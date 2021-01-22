package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PackageConverterTest {

  private final PackageConverter packageConverter = Mappers.getMapper(PackageConverter.class);

  @Test
  void toDto_null() {
    Assertions.assertNull(packageConverter.toDto(null));
  }

  @Test
  void toDto_success() {
    Assertions.assertNotNull(packageConverter.toDto(Package.builder().productId(1).build()));
    Assertions.assertNotNull(packageConverter.toDto(Package.builder().build()));
  }

  @Test
  void toEntity_null() {
    Assertions.assertNull(packageConverter.toEntity(null));
  }

  @Test
  void toEntity_success() {
    Assertions.assertNotNull(packageConverter.toEntity(PackageDto.builder().productId(1).build()));
    Assertions.assertNotNull(packageConverter.toEntity(PackageDto.builder().build()));
  }
}
