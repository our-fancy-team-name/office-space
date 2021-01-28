package com.ourfancyteamname.officespace.db.converters.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PermissionConverterTest {

  private final PermissionConverter permissionConverter = Mappers.getMapper(PermissionConverter.class);

  @Test
  void toDto() {
    assertNull(permissionConverter.toDto(null));
    assertNotNull(permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    assertEquals(PermissionCode.PRCS_EDIT.name(),
        permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()).getCode());
  }
}
