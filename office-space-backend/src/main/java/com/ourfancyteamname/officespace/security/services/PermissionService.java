package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.data.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionService {

  public boolean canDeleteUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsPrinciple user = (UserDetailsPrinciple) authentication.getPrincipal();
    return user.getPermissionCodes().contains(PermissionCode.DELETE_USER);
  }
}
