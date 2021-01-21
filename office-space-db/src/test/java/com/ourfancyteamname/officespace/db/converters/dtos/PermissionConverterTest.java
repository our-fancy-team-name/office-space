package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class PermissionConverterTest {

  private PermissionConverter permissionConverter = Mappers.getMapper(PermissionConverter.class);

  @Test
  public void toDto() {
    Assertions.assertNull(permissionConverter.toDto(null));
    Assertions.assertNotNull(permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    Assertions.assertEquals(PermissionCode.PRCS_EDIT.name(),
        permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()).getCode());
  }
}
