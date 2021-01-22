package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PermissionSecurityServiceTest {
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
  void withoutRole() {
    UserDetailsPrinciple user = UserDetailsPrinciple.builder()
        .email("dang@dang.dang")
        .password("$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC")
        .username("dang")
        .currentRole("")
        .roles(Collections.emptyList())
        .permissionCodes(permissionCodes)
        .build();
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(user);
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditUser());
  }

  @Test
  void canEditUser_true() {
    userDetailsPrinciple.setPermissionCodes(permissionCodes);
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN")).thenReturn(permission);
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditUser());
  }

  @Test
  void canDeleteUser_false() {
    userDetailsPrinciple.setPermissionCodes(Collections.emptyList());
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditRole());
  }

  @Test
  void canEditProduct_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PRD_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditProduct());
  }

  @Test
  void canEditProduct_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PRD_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditProduct());
  }

  @Test
  void canEditPackage_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PKG_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditPackage());
  }

  @Test
  void canEditPackage_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PKG_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditPackage());
  }

  @Test
  void canEditCluster_true() {
    PermissionCode permissionCodeToTest = PermissionCode.CLUS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditCluster());
  }

  @Test
  void canEditCluster_false() {
    PermissionCode permissionCodeToTest = PermissionCode.CLUS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditCluster());
  }

  @Test
  void canEditNode_true() {
    PermissionCode permissionCodeToTest = PermissionCode.NODE_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditNode());
  }

  @Test
  void canEditNode_false() {
    PermissionCode permissionCodeToTest = PermissionCode.NODE_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditNode());
  }

  @Test
  void canEditProcess_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PRCS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertTrue(permissionSecurityService.canEditProcess());
  }

  @Test
  void canEditProcess_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PRCS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    Mockito.when(permissionRepository.findPermissionByRole("SUPER_ADMIN"))
        .thenReturn(Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    Assertions.assertFalse(permissionSecurityService.canEditProcess());
  }
}
