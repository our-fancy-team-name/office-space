package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PackageStatusConverterTest {

  @InjectMocks
  private PackageStatusConverter packageStatusConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String status = packageStatusConverter.convertToDatabaseColumn(PackageStatus.FAIL);
    Assertions.assertEquals(status, PackageStatus.FAIL.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String status = packageStatusConverter.convertToDatabaseColumn(null);
    Assertions.assertEquals(null, status);
  }

  @Test
  public void convertToEntityAttribute_success() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(PackageStatus.FAIL.name());
    Assertions.assertEquals(PackageStatus.FAIL, status);
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void convertToEntityAttribute_errorInvalid() {
//    packageStatusConverter.convertToEntityAttribute("dang");
//  }

  @Test
  public void convertToEntityAttribute_errorNull() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(null);
    Assertions.assertEquals(null, status);
  }
}
