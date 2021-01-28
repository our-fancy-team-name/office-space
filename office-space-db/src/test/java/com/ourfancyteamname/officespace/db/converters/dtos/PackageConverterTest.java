package com.ourfancyteamname.officespace.db.converters.dtos;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PackageConverterTest {

  private final PackageConverter packageConverter = Mappers.getMapper(PackageConverter.class);

  @Test
  void toDto_null() {
    assertNull(packageConverter.toDto(null));
  }

  @Test
  void toDto_success() {
    assertNotNull(packageConverter.toDto(Package.builder().productId(1).build()));
    assertNotNull(packageConverter.toDto(Package.builder().build()));
  }

  @Test
  void toEntity_null() {
    assertNull(packageConverter.toEntity(null));
  }

  @Test
  void toEntity_success() {
    assertNotNull(packageConverter.toEntity(PackageDto.builder().productId(1).build()));
    assertNotNull(packageConverter.toEntity(PackageDto.builder().build()));
  }
}
