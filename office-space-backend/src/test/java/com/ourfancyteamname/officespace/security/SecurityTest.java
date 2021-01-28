package com.ourfancyteamname.officespace.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class SecurityTest {

  @Test
  void testCreatePassword() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    assertTrue(encoder.matches("admin", "$2a$10$ZnoVjM2zmkU5UjJkmEMwce2XRVXZDhEdwYIqIZtGPAgBQEfPj/oAC"));
  }
}
