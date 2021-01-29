package com.ourfancyteamname.officespace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import com.ourfancyteamname.officespace.support.HeaderModifier;

@Lazy
@Configuration
public class TestConfiguration {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @LocalServerPort
  private int port;

  @Bean(name = "restTemplateForTest")
  public RestTemplate getRestTemplateForTest() {
    var restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add(new HeaderModifier(jwtSecret, port));
    return restTemplate;
  }

}
