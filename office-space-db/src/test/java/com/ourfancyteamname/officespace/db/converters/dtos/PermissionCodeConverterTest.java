package com.ourfancyteamname.officespace.db.converters.dtos;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertThrowIllegal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.ourfancyteamname.officespace.db.converters.enums.PermissionCodeConverter;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PermissionCodeConverterTest {

  @InjectMocks
  private PermissionCodeConverter permissionCodeConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(PermissionCode.USER_EDIT);
    assertEquals(permission, PermissionCode.USER_EDIT.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(null);
    assertNull(permission);
  }

  @Test
  void convertToEntityAttribute_success() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(PermissionCode.USER_EDIT.name());
    assertEquals(PermissionCode.USER_EDIT, permission);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    assertThrowIllegal(() -> permissionCodeConverter.convertToEntityAttribute("dang"));
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(null);
    assertNull(permission);
  }
}
