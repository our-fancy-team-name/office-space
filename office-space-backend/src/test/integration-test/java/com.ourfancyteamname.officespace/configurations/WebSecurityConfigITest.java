package com.ourfancyteamname.officespace.configurations;

import com.ourfancyteamname.officespace.annotations.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
class WebSecurityConfigITest {

  @Autowired
  private WebSecurityConfig webSecurityConfig;

  @Test
  void authenticationManagerBean() throws Exception {
    var authenticationManager = webSecurityConfig.authenticationManagerBean();
    Assertions.assertNotNull(authenticationManager);
  }

}
