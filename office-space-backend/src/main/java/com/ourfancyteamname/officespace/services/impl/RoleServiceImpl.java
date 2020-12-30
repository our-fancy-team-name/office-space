package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.db.repos.RoleRepository;
import com.ourfancyteamname.officespace.db.repos.view.RoleUserListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.security.RoleDto;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private SortingService sortingService;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private RoleUserListViewRepository roleUserListViewRepository;

  @Override
  public Role updateRole(RoleDto roleDto) {
    Role target = roleRepository.findById(roleDto.getId())
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
    return roleRepository.save(Role.builder().
        code(roleDto.getAuthority())
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
    Specification<RoleUserListView> specs = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable page = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return roleUserListViewRepository.findAll(specs, page);
  }
}
