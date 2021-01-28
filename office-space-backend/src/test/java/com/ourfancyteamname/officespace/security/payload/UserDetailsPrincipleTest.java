package com.ourfancyteamname.officespace.security.payload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class UserDetailsPrincipleTest {

  @Test
  void userDetailsPrinciple() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Assertions.assertEquals(0, userDetailsPrinciple.getAuthorities().size());
    Assertions.assertTrue(userDetailsPrinciple.isAccountNonExpired());
    Assertions.assertTrue(userDetailsPrinciple.isAccountNonLocked());
    Assertions.assertTrue(userDetailsPrinciple.isCredentialsNonExpired());
    Assertions.assertTrue(userDetailsPrinciple.isEnabled());
  }
}
