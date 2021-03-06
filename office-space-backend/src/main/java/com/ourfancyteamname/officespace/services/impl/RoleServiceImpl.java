package com.ourfancyteamname.officespace.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.view.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.RoleService;

@Service
public class RoleServiceImpl extends AbstractViewServiceImpl<RoleUserListView, RoleUserListViewRepository>
    implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private RoleUserListViewRepository roleUserListViewRepository;

  @Override
  public Role updateRole(RoleDto roleDto) {
    final var target = roleRepository.findById(roleDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));

    if (!roleRepository.findByCode(roleDto.getAuthority())
        .map(Role::getId)
        .map(id -> id == target.getId())
        .orElse(true)) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    target.setCode(roleDto.getAuthority());
    target.setDescription(roleDto.getDescription());
    return roleRepository.save(target);
  }

  @Override
  public Role createRole(RoleDto roleDto) {
    if (roleRepository.existsByCode(roleDto.getAuthority())) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    return roleRepository.save(Role.builder()
        .code(roleDto.getAuthority())
        .description(roleDto.getDescription())
        .build());
  }

  @Override
  public void deleteRole(Integer role) {
    roleRepository.deleteById(role);
  }

  @Override
  public List<String> getRoleCodes() {
    return roleRepository.findAllCode();
  }

  @Override
  public Page<RoleUserListView> getRoleUserListView(TableSearchRequest tableSearchRequest) {
    return findAll(tableSearchRequest);
  }

  @Override
  protected RoleUserListViewRepository getExecutor() {
    return this.roleUserListViewRepository;
  }
}
