package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.security.LoginRequest;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SecurityControllerTest {

  @InjectMocks
  private SecurityController controller;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @Test
  public void authenticateUser() {
    Mockito.when(authenticationManager.authenticate(Mockito.any()))
        .thenReturn(new UsernamePasswordAuthenticationToken(UserDetailsPrinciple.builder().build(), ""));
    controller.authenticateUser(LoginRequest.builder().build());
    Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());
    Mockito.verify(jwtService, Mockito.times(1)).generateJwtToken(Mockito.any());
  }

}
