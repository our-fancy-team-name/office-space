package com.ourfancyteamname.officespace.security.services;


import com.ourfancyteamname.officespace.postgres.entities.Role;
import com.ourfancyteamname.officespace.postgres.entities.User;
import com.ourfancyteamname.officespace.postgres.enums.PermissionCode;
import com.ourfancyteamname.officespace.postgres.repos.PermissionRepository;
import com.ourfancyteamname.officespace.postgres.repos.RoleRepository;
import com.ourfancyteamname.officespace.postgres.repos.UserRepository;
import com.ourfancyteamname.officespace.security.payload.RoleDto;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(error(username));
    List<Role> roles = roleRepository.findByUserId(user.getId());
    if (CollectionUtils.isEmpty(roles)) {
      throw error(username).get();
    }
    Role lastUsage = roleRepository.findLastUsageByUserId(user.getId()).orElse(roles.get(0));
    List<RoleDto> authorities = roles
        .stream()
        .map(role -> new RoleDto(role, lastUsage))
        .collect(Collectors.toList());
    List<PermissionCode> permissionCodes = permissionRepository.findPermissionCodeByRoleId(lastUsage.getId());
    return UserDetailsPrinciple.builder()
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .roles(authorities)
        .permissionCodes(permissionCodes)
        .build();
  }

  private Supplier<UsernameNotFoundException> error(String username) {
    return () -> new UsernameNotFoundException("User Not Found with username: " + username);
  }
}
