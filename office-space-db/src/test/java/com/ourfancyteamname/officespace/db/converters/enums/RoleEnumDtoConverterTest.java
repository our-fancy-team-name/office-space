package com.ourfancyteamname.officespace.db.converters.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.converters.dtos.RoleConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class RoleEnumDtoConverterTest {

  private RoleConverter roleConverter = Mappers.getMapper(RoleConverter.class);

  @Test
  void toDto_success() {
    Role role = new Role();
    role.setCode("SUPER_ADMIN");
    role.setId(1);
    Role activeRole = new Role();
    activeRole.setCode("SUPER_ADMIN");
    activeRole.setId(1);
    Role nonActiveRole = new Role();
    nonActiveRole.setCode("SUPER_ADMIN");
    nonActiveRole.setId(2);
    RoleDto result = roleConverter.toDto(role, activeRole);
    RoleDto result2 = roleConverter.toDto(role, nonActiveRole);
    assertTrue(result.getIsUsing());
    assertFalse(result2.getIsUsing());
  }

  @Test
  void toDto_fail() {
    Role role = new Role();
    role.setCode("SUPER_ADMIN");
    role.setId(1);
    assertNull(roleConverter.toDto(null, null));
    assertNotNull(roleConverter.toDto(role, null));
    assertNotNull(roleConverter.toDto(null, role));
    assertNotNull(roleConverter.toDto(role, role));
  }
}
