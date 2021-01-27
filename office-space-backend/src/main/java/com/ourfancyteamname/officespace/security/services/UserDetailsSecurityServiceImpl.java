package com.ourfancyteamname.officespace.security.services;


import com.ourfancyteamname.officespace.db.converters.dtos.RoleConverter;
import com.ourfancyteamname.officespace.db.repos.PermissionRepository;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.enums.CacheName;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

  @Autowired
  private RoleConverter roleConverter;

  @Override
  @Cacheable(value = CacheName.USER_PRINCIPLE, key = "#username")
  public UserDetailsPrinciple loadUserByUsername(String username) {
    final var user = userRepository.findByUsername(username)
        .orElseThrow(error(username));
    final var roles = roleRepository.findByUserId(user.getId());
    if (CollectionUtils.isEmpty(roles)) {
      throw error(username).get();
    }
    final var lastUsage = roleRepository.findLastUsageByUserId(user.getId()).orElse(roles.get(0));
    final var authorities = roles
        .stream()
        .map(role -> roleConverter.toDto(role, lastUsage))
        .collect(Collectors.toList());
    final var permissionCodes = permissionRepository.findPermissionCodeByRoleId(lastUsage.getId());
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
