package com.ourfancyteamname.officespace.postgres.converters;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.postgres.entities.Role;
import org.junit.Assert;
import org.junit.Test;

public class RoleConverterTest {

  @Test
  public void toDto_success() {
    Role role = new Role();
    role.setCode("ADMIN");
    role.setId(1);

    Role activeRole = new Role();
    activeRole.setCode("ADMIN");
    activeRole.setId(1);

    Role nonActiveRole = new Role();
    nonActiveRole.setCode("USER");
    nonActiveRole.setId(2);
    RoleDto result = RoleConverter.toDto(role, activeRole);
    RoleDto result2 = RoleConverter.toDto(role, nonActiveRole);
    Assert.assertTrue(result.getIsUsing());
    Assert.assertFalse(result2.getIsUsing());
  }
}
