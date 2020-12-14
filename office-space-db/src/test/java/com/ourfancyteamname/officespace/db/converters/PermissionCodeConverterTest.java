package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.enums.PermissionCodeConverter;
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
    String permission = permissionCodeConverter.convertToDatabaseColumn(PermissionCode.USER_EDIT);
    Assert.assertEquals(permission, PermissionCode.USER_EDIT.name());
  }

  @Test
  public void convertToDatabaseColumn_null() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(null);
    Assert.assertEquals(null, permission);
  }

  @Test
  public void convertToEntityAttribute_success() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(PermissionCode.USER_EDIT.name());
    Assert.assertEquals(PermissionCode.USER_EDIT, permission);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorInvalid() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute("dang");
    Assert.assertEquals(PermissionCode.USER_EDIT, permission);
  }

  @Test(expected = IllegalArgumentException.class)
  public void convertToEntityAttribute_errorNull() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(null);
    Assert.assertEquals(PermissionCode.USER_EDIT, permission);
  }
}
