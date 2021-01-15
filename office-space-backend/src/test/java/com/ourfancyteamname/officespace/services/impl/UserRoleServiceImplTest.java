package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceImplTest {

  @InjectMocks
  private UserRoleServiceImpl service;

  @Mock
  private UserRoleListViewRepository userRoleListViewRepository;

  @Test
  public void getExecutor() {
    Assert.assertEquals(userRoleListViewRepository, service.getExecutor());
  }

}