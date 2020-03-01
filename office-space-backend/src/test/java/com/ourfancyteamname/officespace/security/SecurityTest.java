package com.ourfancyteamname.officespace.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityTest {

  @Test
  public void testCreatePassword() {
    BCryptPasswordEncoder a = new BCryptPasswordEncoder();
    System.out.println(a.encode("admin"));
  }
}
