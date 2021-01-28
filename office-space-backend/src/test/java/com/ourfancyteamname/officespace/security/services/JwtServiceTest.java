package com.ourfancyteamname.officespace.security.services;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@UnitTest
class JwtServiceTest {

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(jwtService, "jwtSecret", "jwtSecret");
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 900000);
  }

  @InjectMocks
  private JwtService jwtService;

  @Test
  void generateJwtToken_success() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    String token = jwtService.generateJwtToken(authentication);
    assertTrue(jwtService.validateJwtToken(token));
    assertEquals(jwtService.getUserNameFromJwtToken(token), userDetailsPrinciple.getUsername());
  }

  @Test
  void generateJwtToken_malformedJwtException() {
    assertFalse(jwtService.validateJwtToken("random token"));
  }

  @Test
  void generateJwtToken_illegalArgumentException() {
    assertFalse(jwtService.validateJwtToken(null));
  }

  @Test
  void generateJwtToken_expiredJwtException() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    mockReturn(authentication.getPrincipal(), userDetailsPrinciple);
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 0);
    String token = jwtService.generateJwtToken(authentication);
    assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  void generateJwtToken_signatureException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .signWith(SignatureAlgorithm.HS512, "jwtSecret111")
        .compact();
    assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  void generateJwtToken_unsupportedJwtException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    assertFalse(jwtService.validateJwtToken(token));
  }
}
