package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.db.repos.ProcessNodeRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class NodeServiceImpl implements NodeService {

  @Autowired
  private ProcessNodeRepository processNodeRepository;

  @Autowired
  private ProcessGeneralConverter processGeneralConverter;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Override
  public ProcessNode create(ProcessGeneralDto processGeneralDto) {
    Assert.isTrue(!processNodeRepository.existsByCode(processGeneralDto.getCode()), ErrorCode.DUPLICATED.name());
    return processNodeRepository.save(processGeneralConverter.fromDtoToNodeEntity(processGeneralDto));
  }

  @Override
  public ProcessNode update(ProcessGeneralDto processGeneralDto) {
    ProcessNode target = processNodeRepository.findById(processGeneralDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
    if (!processNodeRepository.findByCode(processGeneralDto.getCode())
        .map(ProcessNode::getCode)
        .map(code -> code.equals(target.getCode()))
        .orElse(true)) {
      throw new IllegalArgumentException(ErrorCode.DUPLICATED.name());
    }
    target.setCode(processGeneralDto.getCode());
    target.setName(processGeneralDto.getName());
    target.setDescription(processGeneralDto.getDescription());
    return processNodeRepository.save(target);
  }

  @Override
  public Page<ProcessGeneralDto> getListView(TableSearchRequest tableSearchRequest) {
    Specification<ProcessNode> specification = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return processNodeRepository.findAll(specification, pageable)
        .map(processGeneralConverter::fromNodeToDto);
  }
}
