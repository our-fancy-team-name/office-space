package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

import java.util.Date;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

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

  @Test
  public void generateJwtToken_signatureException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .signWith(SignatureAlgorithm.HS512, "jwtSecret111")
        .compact();
    Assert.assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generateJwtToken_unsupportedJwtException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    Assert.assertFalse(jwtService.validateJwtToken(token));
  }
}
