package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role updateRole(RoleDto roleDto) {
    Role target = roleRepository.findById(roleDto.getId())
        .orElseThrow(() -> new IllegalArgumentException("Role can not be found"));

    if (!roleRepository.findByCode(roleDto.getAuthority())
        .map(Role::getId)
        .map(id -> id == target.getId())
        .orElse(true)) {
      throw new IllegalArgumentException("Duplicated role code");
    }
    target.setCode(roleDto.getAuthority());
    target.setDescription(roleDto.getDescription());
    return roleRepository.save(target);
  }

  @Override
  public Role createRole(RoleDto roleDto) {
    if (roleRepository.existsByCode(roleDto.getAuthority())) {
      throw new IllegalArgumentException("Duplicated role code");
    }
    return roleRepository.save(Role.builder().
        code(roleDto.getAuthority())
        .description(roleDto.getDescription())
        .build());
  }
}
