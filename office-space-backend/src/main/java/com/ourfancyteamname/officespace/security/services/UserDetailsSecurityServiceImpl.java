package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.repo.UserRepository;
import com.ourfancyteamname.officespace.security.payload.UserDetailsSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(u -> UserDetailsSecurity.builder()
            .id(u.getId())
            .email(u.getEmail())
            .password(u.getPassword())
            .username(u.getUsername())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
  }
}
