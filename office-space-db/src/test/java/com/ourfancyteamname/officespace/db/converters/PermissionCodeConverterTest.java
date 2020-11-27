package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PermissionCodeConverterTest {

  @InjectMocks
  private PermissionCodeConverter permissionCodeConverter;

  @Test
  public void convertToDatabaseColumn_success() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(PermissionCode.DELETE_USER);
    Assert.assertEquals(permission, PermissionCode.DELETE_USER.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(null);
    Assert.assertEquals(null, permission);
  }

  @Test
  public void convertToEntityAttribute_success() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(PermissionCode.DELETE_USER.name());
    Assert.assertEquals(PermissionCode.DELETE_USER, permission);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorInvalid() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute("dang");
    Assert.assertEquals(PermissionCode.DELETE_USER, permission);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorNull() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(null);
    Assert.assertEquals(PermissionCode.DELETE_USER, permission);
  }
}
