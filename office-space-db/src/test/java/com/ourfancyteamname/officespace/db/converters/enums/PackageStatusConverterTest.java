package com.ourfancyteamname.officespace.db.converters.enums;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PackageStatusConverterTest {

  @InjectMocks
  private PackageStatusConverter packageStatusConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String status = packageStatusConverter.convertToDatabaseColumn(PackageStatus.FAIL);
    assertEquals(status, PackageStatus.FAIL.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String status = packageStatusConverter.convertToDatabaseColumn(null);
    assertNull(status);
  }

  @Test
  void convertToEntityAttribute_success() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(PackageStatus.FAIL.name());
    assertEquals(PackageStatus.FAIL, status);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    assertThrowIllegal(() -> packageStatusConverter.convertToEntityAttribute("dang"));
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(null);
    assertNull(status);
  }
}
