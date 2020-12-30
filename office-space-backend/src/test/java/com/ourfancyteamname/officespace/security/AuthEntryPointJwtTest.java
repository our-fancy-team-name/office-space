package com.ourfancyteamname.officespace.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthEntryPointJwtTest {

  @InjectMocks
  AuthEntryPointJwt authEntryPointJwt;

  @Test
  public void commence() throws IOException {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
    AuthenticationException authenticationException = new AuthenticationCredentialsNotFoundException("");
    authEntryPointJwt.commence(mockHttpServletRequest, mockHttpServletResponse, authenticationException);
    Assert.assertEquals("Error: Unauthorized", mockHttpServletResponse.getErrorMessage());
  }
}
