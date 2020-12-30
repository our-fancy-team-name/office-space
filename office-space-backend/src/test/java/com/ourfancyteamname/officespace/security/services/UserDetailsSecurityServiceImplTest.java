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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserDetailsSecurityServiceImplTest {

  private static final User user = User.builder()
      .id(1)
      .username("dang")
      .password("$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC")
      .email("dang@dang.com")
      .build();
  private static final Role admin = new Role(1, "", "SUPER_ADMIN");
  private static final List<Role> roles = Arrays.asList(admin);
  private static final List<PermissionCode> permissionCodes = Arrays.asList(PermissionCode.USER_EDIT);


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
  public void loadUserByUsernameTest_success() {

    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(roleRepository.findLastUsageByUserId(user.getId())).thenReturn(Optional.of(admin));
    Mockito.when(permissionRepository.findPermissionCodeByRoleId(admin.getId())).thenReturn(permissionCodes);
    Mockito.when(roleConverter.toDto(admin, admin))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = (UserDetailsPrinciple) userDetailsSecurityService.loadUserByUsername("dang");
    Assert.assertEquals(result.getUsername(), user.getUsername());
    Assert.assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size()).forEach(i -> {
      Assert.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i));
    });
    Assert.assertEquals(result.getEmail(), user.getEmail());
    Assert.assertEquals(result.getPassword(), user.getPassword());
    Assert.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assert.assertEquals(r.getAuthority(), admin.getCode());
      Assert.assertTrue(r.getIsUsing());
    }
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsernameTest_missingUser() {
    userDetailsSecurityService.loadUserByUsername("dang");
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsernameTest_missingRole() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    userDetailsSecurityService.loadUserByUsername("dang");
  }

  @Test
  public void loadUserByUsernameTest_missingLastRoleUsage() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(permissionRepository.findPermissionCodeByRoleId(admin.getId())).thenReturn(permissionCodes);
    Mockito.when(roleConverter.toDto(Mockito.any(), Mockito.any()))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = (UserDetailsPrinciple) userDetailsSecurityService.loadUserByUsername("dang");
    Assert.assertEquals(result.getUsername(), user.getUsername());
    Assert.assertEquals(result.getPermissionCodes().size(), permissionCodes.size());
    IntStream.range(0, result.getPermissionCodes().size()).forEach(i -> {
      Assert.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i));
    });
    Assert.assertEquals(result.getEmail(), user.getEmail());
    Assert.assertEquals(result.getPassword(), user.getPassword());
    Assert.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assert.assertEquals(r.getAuthority(), admin.getCode());
      Assert.assertTrue(r.getIsUsing());
    }
  }

  @Test
  public void loadUserByUsernameTest_missingPermission() {
    Mockito.when(userRepository.findByUsername("dang")).thenReturn(Optional.of(user));
    Mockito.when(roleRepository.findByUserId(user.getId())).thenReturn(roles);
    Mockito.when(roleRepository.findLastUsageByUserId(user.getId())).thenReturn(Optional.of(admin));
    Mockito.when(roleConverter.toDto(Mockito.any(), Mockito.any()))
        .thenReturn(RoleDto.builder().authority("SUPER_ADMIN").isUsing(true).build());

    UserDetailsPrinciple result = (UserDetailsPrinciple) userDetailsSecurityService.loadUserByUsername("dang");
    Assert.assertEquals(result.getUsername(), user.getUsername());
    Assert.assertEquals(0, result.getPermissionCodes().size());
    IntStream.range(0, result.getPermissionCodes().size()).forEach(i -> {
      Assert.assertEquals(result.getPermissionCodes().get(i), permissionCodes.get(i));
    });
    Assert.assertEquals(result.getEmail(), user.getEmail());
    Assert.assertEquals(result.getPassword(), user.getPassword());
    Assert.assertEquals(result.getRoles().size(), roles.size());
    for (RoleDto r : result.getRoles()) {
      Assert.assertEquals(r.getAuthority(), admin.getCode());
      Assert.assertTrue(r.getIsUsing());
    }
  }

}
