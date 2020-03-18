package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

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

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(jwtService, "jwtSecret", "jwtSecret");
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 900000);
  }

  @InjectMocks
  private JwtService jwtService;

  @Test
  public void generateJwtToken_success() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    String token = jwtService.generateJwtToken(authentication);
    Assert.assertTrue(jwtService.validateJwtToken(token));
    Assert.assertEquals(jwtService.getUserNameFromJwtToken(token), userDetailsPrinciple.getUsername());
  }

  @Test
  public void generateJwtToken_malformedJwtException() {
    Assert.assertFalse(jwtService.validateJwtToken("random token"));
  }

  @Test
  public void generateJwtToken_illegalArgumentException() {
    Assert.assertFalse(jwtService.validateJwtToken(null));
  }

  @Test
  public void generateJwtToken_expiredJwtException() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 0);
    String token = jwtService.generateJwtToken(authentication);
    Assert.assertFalse(jwtService.validateJwtToken(token));
  }
}
