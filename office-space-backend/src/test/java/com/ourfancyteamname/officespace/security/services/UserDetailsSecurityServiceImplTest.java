package com.ourfancyteamname.officespace.security.services;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ourfancyteamname.officespace.db.converters.dtos.RoleConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class UserDetailsSecurityServiceImplTest {

  private static final User user = User.builder()
      .id(1)
      .username("dang")
      .password("$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC")
      .email("dang@dang.com")
      .build();
  private static final Role admin = new Role(1, "", "SUPER_ADMIN");
  private static final List<Role> roles = Collections.singletonList(admin);
  private static final List<PermissionCode> permissionCodes = Collections.singletonList(PermissionCode.USER_EDIT);


  @InjectMocks
  private UserDetailsSecurityServiceImpl userDetailsSecurityService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private PermissionRepository permissionRepository;

  @Mock
  private RoleConverter roleConverter;

  @Test
  void loadUserByUsernameTest_success() {

    mockReturn(userRepository.findByUsername("dang"), Optional.of(user));
    mockReturn(roleRepository.findByUserId(user.getId()), roles);
    mockReturn(roleRepository.findLastUsageByUserId(user.getId()), Optional.of(admin));
    mockReturn(permissionRepository.findPermissionCodeByRoleId(admin.getId()), permissionCodes);
    mockReturn(roleConverter.toDto(admin, admin),
        RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    assertEquals(result.getUsername(), user.getUsername());
    assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    assertEquals(result.getEmail(), user.getEmail());
    assertEquals(result.getPassword(), user.getPassword());
    assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      assertEquals(r.getAuthority(), admin.getCode());
      assertTrue(r.getIsUsing());
    }
  }

  @Test
  void loadUserByUsernameTest_missingUser() {
    assertThrows(
        UsernameNotFoundException.class,
        () -> userDetailsSecurityService.loadUserByUsername("dang")
    );
  }

  @Test
  void loadUserByUsernameTest_missingRole() {
    mockReturn(userRepository.findByUsername("dang"), Optional.of(user));
    assertThrows(
        UsernameNotFoundException.class,
        () -> userDetailsSecurityService.loadUserByUsername("dang")
    );
  }

  @Test
  void loadUserByUsernameTest_missingLastRoleUsage() {
    mockReturn(userRepository.findByUsername("dang"), Optional.of(user));
    mockReturn(roleRepository.findByUserId(user.getId()), roles);
    mockReturn(permissionRepository.findPermissionCodeByRoleId(admin.getId()), permissionCodes);
    mockReturn(roleConverter.toDto(any(), any()),
        RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    assertEquals(result.getUsername(), user.getUsername());
    assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    assertEquals(result.getEmail(), user.getEmail());
    assertEquals(result.getPassword(), user.getPassword());
    assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      assertEquals(r.getAuthority(), admin.getCode());
      assertTrue(r.getIsUsing());
    }
  }

  @Test
  void loadUserByUsernameTest_missingPermission() {
    mockReturn(userRepository.findByUsername("dang"), Optional.of(user));
    mockReturn(roleRepository.findByUserId(user.getId()), roles);
    mockReturn(roleRepository.findLastUsageByUserId(user.getId()), Optional.of(admin));
    mockReturn(roleConverter.toDto(any(), any()),
        RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    assertEquals(result.getUsername(), user.getUsername());
    assertEquals(0, result.getPermissionCodes().size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    assertEquals(result.getEmail(), user.getEmail());
    assertEquals(result.getPassword(), user.getPassword());
    assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      assertEquals(r.getAuthority(), admin.getCode());
      assertTrue(r.getIsUsing());
    }
  }

}
