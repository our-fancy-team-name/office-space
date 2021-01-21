package com.ourfancyteamname.officespace.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class AuthEntryPointJwtTest {

  @InjectMocks
  AuthEntryPointJwt authEntryPointJwt;

  @Test
  public void commence() throws IOException {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    AuthenticationException authenticationException = new AuthenticationCredentialsNotFoundException("");
    authEntryPointJwt.commence(mockHttpServletRequest, mockHttpServletResponse, authenticationException);
    Assertions.assertEquals("Error: Unauthorized", mockHttpServletResponse.getErrorMessage());
  }
}
