package com.ourfancyteamname.officespace.security;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

  private static final String username = "dang";

  private static final String token = Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + 900000))
      .signWith(SignatureAlgorithm.HS512, "jwtSecret")
      .compact();

  @InjectMocks
  AuthTokenFilter authTokenFilter;

  @Mock
  private JwtService jwtService;

  @Mock
  private UserDetailsSecurityServiceImpl userDetailsService;

  @BeforeEach
  public void reset() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  public void doFilterInternal_Sucess() throws ServletException, IOException {
    Mockito.when(jwtService.validateJwtToken(token)).thenReturn(true);
    Mockito.when(jwtService.getUserNameFromJwtToken(token)).thenReturn(username);
    Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(new UserDetailsPrinciple());

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", JwtService.TOKEN_TYPE + " " + token);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  public void doFilterInternal_noAuthorization() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    Mockito.verify(jwtService, Mockito.times(1)).validateJwtToken(Mockito.any());
  }

  @Test
  public void doFilterInternal_noAuthorization2() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", token);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    Mockito.verify(jwtService, Mockito.times(1)).validateJwtToken(Mockito.any());
  }

  @Test
  public void doFilterInternal_noAuthorization3() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    String tokenFailed = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    request.addHeader("Authorization", tokenFailed);
    request.addHeader("Role", "SUPER_ADMIN");
    authTokenFilter.doFilterInternal(request, new MockHttpServletResponse(), new MockFilterChain());
    Mockito.verify(jwtService, Mockito.times(1)).validateJwtToken(Mockito.any());
  }
}
