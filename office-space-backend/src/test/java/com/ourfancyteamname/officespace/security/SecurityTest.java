package com.ourfancyteamname.officespace.security;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class SecurityTest {

  @Test
  void testCreatePassword() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    Assert.assertTrue(encoder.matches("admin", "$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC"));
  }
}
