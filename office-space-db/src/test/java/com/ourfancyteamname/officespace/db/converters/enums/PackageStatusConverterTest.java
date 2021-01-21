package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PackageStatusConverterTest {

  @InjectMocks
  private PackageStatusConverter packageStatusConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String status = packageStatusConverter.convertToDatabaseColumn(PackageStatus.FAIL);
    Assertions.assertEquals(status, PackageStatus.FAIL.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String status = packageStatusConverter.convertToDatabaseColumn(null);
    Assertions.assertNull(status);
  }

  @Test
  void convertToEntityAttribute_success() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(PackageStatus.FAIL.name());
    Assertions.assertEquals(PackageStatus.FAIL, status);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> packageStatusConverter.convertToEntityAttribute("dang")
    );
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(null);
    Assertions.assertNull(status);
  }
}
