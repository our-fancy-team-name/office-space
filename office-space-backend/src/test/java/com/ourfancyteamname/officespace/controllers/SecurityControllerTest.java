package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.info.GitProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ourfancyteamname.officespace.dtos.security.LoginRequest;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
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
    mockReturn(authenticationManager.authenticate(any()),
        new UsernamePasswordAuthenticationToken(UserDetailsPrinciple.builder().build(), ""));
    controller.authenticateUser(LoginRequest.builder().build());
    verifyInvoke1Time(authenticationManager).authenticate(any());
    verifyInvoke1Time(jwtService).generateJwtToken(any());
  }

  @Test
  void version() {
    controller.version();
    verifyInvoke1Time(gitProperties).getBranch();
    verifyInvoke1Time(gitProperties).getShortCommitId();
    verifyInvoke1Time(gitProperties).get("build.time");
  }

}
