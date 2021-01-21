package com.ourfancyteamname.officespace.security.payload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDetailsPrincipleTest {

  @Test
  public void userDetailsPrinciple() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Assertions.assertEquals(0, userDetailsPrinciple.getAuthorities().size());
    Assertions.assertTrue(userDetailsPrinciple.isAccountNonExpired());
    Assertions.assertTrue(userDetailsPrinciple.isAccountNonLocked());
    Assertions.assertTrue(userDetailsPrinciple.isCredentialsNonExpired());
    Assertions.assertTrue(userDetailsPrinciple.isEnabled());
  }
}
