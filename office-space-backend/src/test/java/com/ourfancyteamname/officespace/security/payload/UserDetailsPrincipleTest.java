package com.ourfancyteamname.officespace.security.payload;

import org.junit.Assert;
import org.junit.Test;

public class UserDetailsPrincipleTest {

  @Test
  public void userDetailsPrinciple() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Assert.assertEquals(0, userDetailsPrinciple.getAuthorities().size());
    Assert.assertTrue(userDetailsPrinciple.isAccountNonExpired());
    Assert.assertTrue(userDetailsPrinciple.isAccountNonLocked());
    Assert.assertTrue(userDetailsPrinciple.isCredentialsNonExpired());
    Assert.assertTrue(userDetailsPrinciple.isEnabled());
  }
}
