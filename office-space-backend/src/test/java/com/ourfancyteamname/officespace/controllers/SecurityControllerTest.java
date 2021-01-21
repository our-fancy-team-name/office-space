package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.dtos.security.LoginRequest;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.info.GitProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

  @InjectMocks
  private SecurityController controller;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @Mock
  private GitProperties gitProperties;

  @Test
  void authenticateUser() {
    Mockito.when(authenticationManager.authenticate(Mockito.any()))
        .thenReturn(new UsernamePasswordAuthenticationToken(UserDetailsPrinciple.builder().build(), ""));
    controller.authenticateUser(LoginRequest.builder().build());
    Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());
    Mockito.verify(jwtService, Mockito.times(1)).generateJwtToken(Mockito.any());
  }

  @Test
  void version() {
    controller.version();
    Mockito.verify(gitProperties, Mockito.times(1)).getBranch();
    Mockito.verify(gitProperties, Mockito.times(1)).getShortCommitId();
    Mockito.verify(gitProperties, Mockito.times(1)).get("build.time");
  }

}
