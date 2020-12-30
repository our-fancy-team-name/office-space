package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ClusterServiceImpl implements ClusterService {

  @Autowired
  private ProcessClusterRepository processClusterRepository;

  @Autowired
  private ProcessGeneralConverter processGeneralConverter;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Override
  public ProcessCluster create(ProcessGeneralDto processGeneralDto) {
    Assert.isTrue(!processClusterRepository.existsByCode(processGeneralDto.getCode()), ErrorCode.DUPLICATED.name());
    return processClusterRepository.save(processGeneralConverter.fromDtoToClusterEntity(processGeneralDto));
  }

  @Override
  public Page<ProcessGeneralDto> getListView(TableSearchRequest tableSearchRequest) {
    Specification<ProcessCluster> specification = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return processClusterRepository.findAll(specification, pageable)
        .map(processGeneralConverter::fromClusterToDto);
  }

  @Override
  public ProcessCluster update(ProcessGeneralDto processGeneralDto) {
    ProcessCluster target = processClusterRepository.findById(processGeneralDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));

    if (!processClusterRepository.findByCode(processGeneralDto.getCode())
        .map(ProcessCluster::getCode)
        .map(code -> code.equals(target.getCode()))
        .orElse(true)) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    target.setCode(processGeneralDto.getCode());
    target.setName(processGeneralDto.getName());
    target.setDescription(processGeneralDto.getDescription());
    return processClusterRepository.save(target);
  }
}
