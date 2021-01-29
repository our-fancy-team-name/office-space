package com.ourfancyteamname.officespace.support;

import java.util.Date;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.ourfancyteamname.officespace.security.services.JwtService;
import com.ourfancyteamname.officespace.test.enums.RestClientConst;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;

public class HeaderModifier implements ClientHttpRequestInterceptor {

  private final String token;
  private final int port;

  public HeaderModifier(String jwtSecret, int port) {
    this.token = JwtService.TOKEN_TYPE + " " + Jwts.builder()
        .setSubject(RestClientConst.USER)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + Integer.MAX_VALUE))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
    this.port = port;
  }

  @Override
  @SneakyThrows
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
    request.getHeaders().add("Authorization", token);
    request.getHeaders().add("Role", RestClientConst.SUPER_ADMIN);
    return execution.execute(new HttpRequestModifier(request, port), body);
  }
}
