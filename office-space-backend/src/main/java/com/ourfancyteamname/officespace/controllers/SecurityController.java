package com.ourfancyteamname.officespace.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.dtos.security.LoginRequest;
import com.ourfancyteamname.officespace.security.payload.JwtResponse;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import com.ourfancyteamname.officespace.security.services.JwtService;

@RestController
@RequestMapping("/auth")
public class SecurityController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private GitProperties gitProperties;

  @PostMapping("/signIn")
  public ResponseEntity<JwtResponse<UserDetails>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    final var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final var userDetails = (UserDetailsPrinciple) authentication.getPrincipal();
    return ResponseEntity.ok(JwtResponse.builder()
        .token(jwtService.generateJwtToken(authentication))
        .type(JwtService.TOKEN_TYPE)
        .userDetails(userDetails)
        .build());
  }

  @GetMapping("/version")
  public ResponseEntity<Map<String, String>> version() {
    Map<String, String> result = new LinkedHashMap<>();
    result.put("branch", gitProperties.getBranch());
    result.put("commitId", gitProperties.getShortCommitId());
    result.put("buildTime", gitProperties.get("build.time"));
    return ResponseEntity.ok(result);
  }
}
