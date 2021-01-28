package com.ourfancyteamname.officespace.security.services;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ourfancyteamname.officespace.db.converters.dtos.PermissionConverter;
import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), user);
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditUser());
  }

  @Test
  void canEditUser_true() {
    userDetailsPrinciple.setPermissionCodes(permissionCodes);
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"), permission);
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditUser());
  }

  @Test
  void canDeleteUser_false() {
    userDetailsPrinciple.setPermissionCodes(Collections.emptyList());
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditRole());
  }

  @Test
  void canEditProduct_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PRD_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditProduct());
  }

  @Test
  void canEditProduct_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PRD_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditProduct());
  }

  @Test
  void canEditPackage_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PKG_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditPackage());
  }

  @Test
  void canEditPackage_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PKG_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditPackage());
  }

  @Test
  void canEditCluster_true() {
    PermissionCode permissionCodeToTest = PermissionCode.CLUS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditCluster());
  }

  @Test
  void canEditCluster_false() {
    PermissionCode permissionCodeToTest = PermissionCode.CLUS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditCluster());
  }

  @Test
  void canEditNode_true() {
    PermissionCode permissionCodeToTest = PermissionCode.NODE_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN")
        , Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditNode());
  }

  @Test
  void canEditNode_false() {
    PermissionCode permissionCodeToTest = PermissionCode.NODE_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditNode());
  }

  @Test
  void canEditProcess_true() {
    PermissionCode permissionCodeToTest = PermissionCode.PRCS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(permissionCodeToTest).build()));
    SecurityContextHolder.setContext(securityContext);
    assertTrue(permissionSecurityService.canEditProcess());
  }

  @Test
  void canEditProcess_false() {
    PermissionCode permissionCodeToTest = PermissionCode.PRCS_EDIT;
    userDetailsPrinciple.setPermissionCodes(Arrays.asList(permissionCodeToTest));
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    mockReturn(securityContext.getAuthentication(), authentication);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    mockReturn(permissionRepository.findPermissionByRole("SUPER_ADMIN"),
        Arrays.asList(Permission.builder().code(null).build()));
    SecurityContextHolder.setContext(securityContext);
    assertFalse(permissionSecurityService.canEditProcess());
  }
}
