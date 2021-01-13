package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PackageStatusConverterTest {

  @InjectMocks
  private PackageStatusConverter packageStatusConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String status = packageStatusConverter.convertToDatabaseColumn(PackageStatus.FAIL);
    Assert.assertEquals(status, PackageStatus.FAIL.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String status = packageStatusConverter.convertToDatabaseColumn(null);
    Assert.assertEquals(null, status);
  }

  @Test
  public void convertToEntityAttribute_success() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(PackageStatus.FAIL.name());
    Assert.assertEquals(PackageStatus.FAIL, status);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorInvalid() {
    packageStatusConverter.convertToEntityAttribute("dang");
  }

  @Test
  public void convertToEntityAttribute_errorNull() {
    PackageStatus status = packageStatusConverter.convertToEntityAttribute(null);
    Assert.assertEquals(null, status);
  }
}
