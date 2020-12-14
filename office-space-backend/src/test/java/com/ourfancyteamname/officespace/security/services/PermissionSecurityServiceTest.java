package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PermissionSecurityServiceTest {
  private static final RoleDto admin = RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build();
  private static final List<RoleDto> roles = Arrays.asList(admin);
  private static final List<PermissionCode> permissionCodes = Arrays.asList(PermissionCode.USER_EDIT);
  private static final List<Permission> permission =
      Arrays.asList(Permission.builder().code(PermissionCode.USER_EDIT).build());


  private static final UserDetailsPrinciple userDetailsPrinciple = UserDetailsPrinciple.builder()
      .email("dang@dang.dang")
      .password("$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC")
      .username("dang")
      .currentRole("SUPER_ADMIN")
      .roles(roles)
      .permissionCodes(permissionCodes)
      .build();


  @InjectMocks
  private PermissionSecurityService permissionSecurityService;

  @Mock
  private PermissionRepository permissionRepository;

  @Mock
  private PermissionConverter permissionConverter;

  @Test
  public void canEditUser_true() {
    userDetailsPrinciple.setPermissionCodes(permissionCodes);
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN")).thenReturn(permission);
    SecurityContextHolder.setContext(securityContext);
    Assert.assertTrue(permissionSecurityService.canEditUser());
  }

  @Test
  public void canDeleteUser_false() {
    userDetailsPrinciple.setPermissionCodes(Collections.emptyList());
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    SecurityContextHolder.setContext(securityContext);
    Assert.assertFalse(permissionSecurityService.canEditRole());
  }
}
