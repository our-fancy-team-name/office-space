package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.dtos.RoleDtoConverter;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.enums.RoleEnum;

import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleEnumDtoConverterTest {

  private RoleDtoConverter roleDtoConverter = Mappers.getMapper(RoleDtoConverter.class);

  @Test
  public void toDto_success() {
    Role role = new Role();
    role.setCode(RoleEnum.SUPER_ADMIN);
    role.setId(1);
    Role activeRole = new Role();
    activeRole.setCode(RoleEnum.SUPER_ADMIN);
    activeRole.setId(1);
    Role nonActiveRole = new Role();
    nonActiveRole.setCode(RoleEnum.SUPER_ADMIN);
    nonActiveRole.setId(2);
    RoleDto result = roleDtoConverter.toDto(role, activeRole);
    RoleDto result2 = roleDtoConverter.toDto(role, nonActiveRole);
    Assert.assertTrue(result.getIsUsing());
    Assert.assertFalse(result2.getIsUsing());
  }
}
