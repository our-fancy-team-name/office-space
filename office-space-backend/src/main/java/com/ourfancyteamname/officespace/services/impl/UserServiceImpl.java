package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.dtos.UserDto;
import com.ourfancyteamname.officespace.db.converters.UserConverter;
import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.db.repos.UserRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private UserConverter userConverter;

  @Override
  public Page<UserDto> findAllByPaging(TableSearchRequest tableSearchRequest) {
    Specification<User> specs = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    if (tableSearchRequest.getPagingRequest().getPageSize() == 0) {
      throw new IllegalArgumentException();
    }
    Pageable page = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return userRepository.findAll(specs, page)
        .map(userConverter::toDto);
  }
}
