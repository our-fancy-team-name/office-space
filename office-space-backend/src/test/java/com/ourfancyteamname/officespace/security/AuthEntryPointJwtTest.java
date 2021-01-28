package com.ourfancyteamname.officespace.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class AuthEntryPointJwtTest {

  @InjectMocks
  private AuthEntryPointJwt authEntryPointJwt;

  @Test
  void commence() throws IOException {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    AuthenticationException authenticationException = new AuthenticationCredentialsNotFoundException("");
    authEntryPointJwt.commence(mockHttpServletRequest, mockHttpServletResponse, authenticationException);
    assertEquals("Error: Unauthorized", mockHttpServletResponse.getErrorMessage());
  }
}
