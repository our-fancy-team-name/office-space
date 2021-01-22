package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.converters.enums.PermissionCodeConverter;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PermissionCodeConverterTest {

  @InjectMocks
  private PermissionCodeConverter permissionCodeConverter;

  @Test
  void convertToDatabaseColumn_success() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(PermissionCode.USER_EDIT);
    Assertions.assertEquals(permission, PermissionCode.USER_EDIT.name());
  }

  @Test
  void convertToDatabaseColumn_null() {
    String permission = permissionCodeConverter.convertToDatabaseColumn(null);
    Assertions.assertNull(permission);
  }

  @Test
  void convertToEntityAttribute_success() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(PermissionCode.USER_EDIT.name());
    Assertions.assertEquals(PermissionCode.USER_EDIT, permission);
  }

  @Test
  void convertToEntityAttribute_errorInvalid() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> permissionCodeConverter.convertToEntityAttribute("dang")
    );
  }

  @Test
  void convertToEntityAttribute_errorNull() {
    PermissionCode permission = permissionCodeConverter.convertToEntityAttribute(null);
    Assertions.assertNull(permission);
  }
}
