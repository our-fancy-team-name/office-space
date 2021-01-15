package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import com.ourfancyteamname.officespace.db.repos.view.UserRoleListViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends AbstractViewServiceImpl<UserRoleListView, UserRoleListViewRepository> {

  @Autowired
  private UserRoleListViewRepository userRoleListViewRepository;

  @Override
  public UserRoleListViewRepository getExecutor() {
    return this.userRoleListViewRepository;
  }
}
