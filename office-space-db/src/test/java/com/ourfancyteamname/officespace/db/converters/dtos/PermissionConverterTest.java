package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionConverterTest {

  private PermissionConverter permissionConverter = Mappers.getMapper(PermissionConverter.class);

  @Test
  public void toDto() {
    Assert.assertNull(permissionConverter.toDto(null));
    Assert.assertNotNull(permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()));
    Assert.assertEquals(PermissionCode.PRCS_EDIT.name(),
        permissionConverter.toDto(Permission.builder().code(PermissionCode.PRCS_EDIT).build()).getCode());
  }
}
