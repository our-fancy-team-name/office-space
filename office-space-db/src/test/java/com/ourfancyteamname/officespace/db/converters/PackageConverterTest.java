package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.dtos.PackageConverter;
import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PackageConverterTest {

  private PackageConverter packageConverter = Mappers.getMapper(PackageConverter.class);

  @Test
  public void toDto_null() {
    Assert.assertNull(packageConverter.toDto(null));
  }

  @Test
  public void toDto_success() {
    Assert.assertNotNull(packageConverter.toDto(Package.builder().productId(1).build()));
    Assert.assertNotNull(packageConverter.toDto(Package.builder().build()));
  }

  @Test
  public void toEntity_null() {
    Assert.assertNull(packageConverter.toEntity(null));
  }

  @Test
  public void toEntity_success() {
    Assert.assertNotNull(packageConverter.toEntity(PackageDto.builder().productId(1).build()));
    Assert.assertNotNull(packageConverter.toEntity(PackageDto.builder().build()));
  }
}
