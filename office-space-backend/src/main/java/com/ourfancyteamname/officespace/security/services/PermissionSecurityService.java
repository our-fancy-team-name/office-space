package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.db.entities.Permission;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionSecurityService {

  @Autowired
  private PermissionRepository permissionRepository;

  public boolean canDeleteUser() {
    return checkPermission(PermissionCode.USER_DELETE);
  }

  public boolean canEditRole() {
    return checkPermission(PermissionCode.ROLE_EDIT);
  }

  private boolean checkPermission(PermissionCode code) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsPrinciple user = (UserDetailsPrinciple) authentication.getPrincipal();
    if (StringUtils.isBlank(user.getCurrentRole())) {
      return false;
    }
    return permissionRepository.findPermissionByRole(user.getCurrentRole()).stream()
        .map(Permission::getCode)
        .anyMatch(code::equals);
  }
}