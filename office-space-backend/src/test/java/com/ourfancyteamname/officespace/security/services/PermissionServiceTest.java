package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
public class PermissionServiceTest {
  private static final RoleDto admin = new RoleDto("ADMIN", true);
  private static final List<RoleDto> roles = Arrays.asList(admin);
  private static final List<PermissionCode> permissionCodes = Arrays.asList(PermissionCode.DELETE_USER);

  private static final UserDetailsPrinciple userDetailsPrinciple = UserDetailsPrinciple.builder()
      .email("dang@dang.dang")
      .password("$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC")
      .username("dang")
      .roles(roles)
      .permissionCodes(permissionCodes)
      .build();


  @InjectMocks
  private PermissionService permissionService;

  @Test
  public void canDeleteUser_true() {
    userDetailsPrinciple.setPermissionCodes(permissionCodes);
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    SecurityContextHolder.setContext(securityContext);
    Assert.assertTrue(permissionService.canDeleteUser());
  }

  @Test
  public void canDeleteUser_false() {
    userDetailsPrinciple.setPermissionCodes(Collections.emptyList());
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    SecurityContextHolder.setContext(securityContext);
    Assert.assertFalse(permissionService.canDeleteUser());
  }
}
