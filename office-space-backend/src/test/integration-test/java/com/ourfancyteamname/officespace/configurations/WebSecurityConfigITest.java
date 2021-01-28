package com.ourfancyteamname.officespace.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ourfancyteamname.officespace.test.annotations.IntegrationTest;

@IntegrationTest
class WebSecurityConfigITest {

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Test
  void bean() throws Exception {
    Assertions.assertNotNull(webSecurityConfig.authenticationManagerBean());
  }
}
