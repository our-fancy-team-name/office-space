package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.data.Role;
import com.ourfancyteamname.officespace.data.User;
import com.ourfancyteamname.officespace.data.enums.PermissionCode;
import com.ourfancyteamname.officespace.repo.PermissionRepository;
import com.ourfancyteamname.officespace.repo.RoleRepository;
import com.ourfancyteamname.officespace.repo.UserRepository;
import com.ourfancyteamname.officespace.security.payload.AuthorityPrinciple;
import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
    List<GrantedAuthority> authorities = roles
        .stream()
        .map(role -> new AuthorityPrinciple(role, lastUsage))
        .collect(Collectors.toList());
    List<PermissionCode> permissionCodes = permissionRepository.findPermissionCodeByRoleId(lastUsage.getId());
    return UserDetailsPrinciple.builder()
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .authorities(authorities)
        .permissionCodes(permissionCodes)
        .build();
  }

  private Supplier<UsernameNotFoundException> error(String username) {
    return () -> new UsernameNotFoundException("User Not Found with username: " + username);
  }
}
