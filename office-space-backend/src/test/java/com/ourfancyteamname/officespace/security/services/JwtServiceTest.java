package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

  @BeforeEach
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
    Assertions.assertTrue(jwtService.validateJwtToken(token));
    Assertions.assertEquals(jwtService.getUserNameFromJwtToken(token), userDetailsPrinciple.getUsername());
  }

  @Test
  public void generateJwtToken_malformedJwtException() {
    Assertions.assertFalse(jwtService.validateJwtToken("random token"));
  }

  @Test
  public void generateJwtToken_illegalArgumentException() {
    Assertions.assertFalse(jwtService.validateJwtToken(null));
  }

  @Test
  public void generateJwtToken_expiredJwtException() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 0);
    String token = jwtService.generateJwtToken(authentication);
    Assertions.assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generateJwtToken_signatureException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .signWith(SignatureAlgorithm.HS512, "jwtSecret111")
        .compact();
    Assertions.assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generateJwtToken_unsupportedJwtException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    Assertions.assertFalse(jwtService.validateJwtToken(token));
  }
}
