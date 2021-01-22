package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.db.converters.dtos.RoleConverter;
import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@ExtendWith(MockitoExtension.class)
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

    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(roleRepository.findLastUsageByUserId(user.getId())).thenReturn(Optional.of(admin));
    Mockito.when(permissionRepository.findPermissionCodeByRoleId(admin.getId())).thenReturn(permissionCodes);
    Mockito.when(roleConverter.toDto(admin, admin))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    Assertions.assertEquals(result.getUsername(), user.getUsername());
    Assertions.assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> Assertions.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    Assertions.assertEquals(result.getEmail(), user.getEmail());
    Assertions.assertEquals(result.getPassword(), user.getPassword());
    Assertions.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assertions.assertEquals(r.getAuthority(), admin.getCode());
      Assertions.assertTrue(r.getIsUsing());
    }
  }

  @Test
  void loadUserByUsernameTest_missingUser() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> userDetailsSecurityService.loadUserByUsername("dang")
    );
  }

  @Test
  void loadUserByUsernameTest_missingRole() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> userDetailsSecurityService.loadUserByUsername("dang")
    );
  }

  @Test
  void loadUserByUsernameTest_missingLastRoleUsage() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(permissionRepository.findPermissionCodeByRoleId(admin.getId())).thenReturn(permissionCodes);
    Mockito.when(roleConverter.toDto(Mockito.any(), Mockito.any()))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    Assertions.assertEquals(result.getUsername(), user.getUsername());
    Assertions.assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> Assertions.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    Assertions.assertEquals(result.getEmail(), user.getEmail());
    Assertions.assertEquals(result.getPassword(), user.getPassword());
    Assertions.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assertions.assertEquals(r.getAuthority(), admin.getCode());
      Assertions.assertTrue(r.getIsUsing());
    }
  }

  @Test
  void loadUserByUsernameTest_missingPermission() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(roleRepository.findLastUsageByUserId(user.getId())).thenReturn(Optional.of(admin));
    Mockito.when(roleConverter.toDto(Mockito.any(), Mockito.any()))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = userDetailsSecurityService.loadUserByUsername("dang");
    Assertions.assertEquals(result.getUsername(), user.getUsername());
    Assertions.assertEquals(0, result.getPermissionCodes().size());
    IntStream.range(0, result.getPermissionCodes().size())
        .forEach(i -> Assertions.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i)));
    Assertions.assertEquals(result.getEmail(), user.getEmail());
    Assertions.assertEquals(result.getPassword(), user.getPassword());
    Assertions.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assertions.assertEquals(r.getAuthority(), admin.getCode());
      Assertions.assertTrue(r.getIsUsing());
    }
  }

}
