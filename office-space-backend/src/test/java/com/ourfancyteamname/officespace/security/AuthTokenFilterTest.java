package com.ourfancyteamname.officespace.security;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@UnitTest
class AuthTokenFilterTest {

  private static final String username = "dang";

  private static final String token = Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + 900000))
      .signWith(SignatureAlgorithm.HS512, "jwtSecret")
      .compact();

  @InjectMocks
  private AuthTokenFilter authTokenFilter;

  @Mock
  private JwtService jwtService;

  @Mock
  private UserDetailsSecurityServiceImpl userDetailsService;

  @BeforeEach
  void reset() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  void doFilterInternal_Sucess() throws ServletException, IOException {
    mockReturn(jwtService.validateJwtToken(token), true);
    mockReturn(jwtService.getUserNameFromJwtToken(token), username);
    mockReturn(userDetailsService.loadUserByUsername(username), new UserDetailsPrinciple());

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", JwtService.TOKEN_TYPE + " " + token);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void doFilterInternal_noAuthorization() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    verifyInvoke1Time(jwtService).validateJwtToken(any());
  }

  @Test
  void doFilterInternal_noAuthorization2() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", token);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    verifyInvoke1Time(jwtService).validateJwtToken(any());
  }

  @Test
  void doFilterInternal_noAuthorization3() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    String tokenFailed = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    request.addHeader("Authorization", tokenFailed);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    verifyInvoke1Time(jwtService).validateJwtToken(any());
  }
}
