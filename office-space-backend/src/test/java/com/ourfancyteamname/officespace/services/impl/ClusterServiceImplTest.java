package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClusterServiceImplTest {

  private static final String code = "code";

  @InjectMocks
  private ClusterServiceImpl service;

  @Mock
  private ProcessClusterRepository processClusterRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock
  private SpecificationBuilderService<ProcessCluster> specificationBuilderService;

  @Mock
  private PaginationBuilderService paginationBuilderService;

  @Mock
  private SortingBuilderService sortingBuilderService;

  @Test
  void create_duplicatedCode() {
    Mockito.when(processClusterRepository.existsByCode(code)).thenReturn(true);
    var dto = ProcessGeneralDto.builder().code(code).build();
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(dto), ErrorCode.DUPLICATED.name());
  }

  @Test
  void create_success() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().code(code).build();
    Mockito.when(processClusterRepository.existsByCode(code)).thenReturn(false);
    ProcessCluster result = processGeneralConverter.fromDtoToClusterEntity(data);
    Mockito.when(processClusterRepository.save(result)).thenReturn(result);
    service.create(data);
    Mockito.verify(processClusterRepository, Mockito.times(1)).existsByCode(Mockito.any());
    Mockito.verify(processClusterRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification<ProcessCluster> specs = specificationBuilderService.from(tableSearchRequest);
    Pageable page = PageRequest.of(1, 2);
    Page<ProcessCluster> result =
        new PageImpl<>(Collections.singletonList(ProcessCluster.builder().code(code).build()));
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(page);
    Mockito.when(service.getExecutor().findAll(specs, page)).thenReturn(result);
    Page<ProcessGeneralDto> processGeneralDtos = service.getListView(tableSearchRequest);
    Assertions.assertEquals(1, processGeneralDtos.getTotalElements());
  }

  @Test
  void update_notFound() {
    var data = ProcessGeneralDto.builder().id(1).code(code).build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.empty());
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(data), ErrorCode.NOT_FOUND.name());
  }

  @Test
  void update_duplicate() {
    var data = ProcessGeneralDto.builder().id(1).code(code).build();
    var dupData = ProcessCluster.builder().id(2).code(code).build();
    var cluster = ProcessCluster.builder().build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.of(cluster));
    Mockito.when(processClusterRepository.findByCode(code)).thenReturn(Optional.of(dupData));
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void update() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().id(1).code(code).build();
    ProcessCluster cluster = ProcessCluster.builder().build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.of(cluster));
    Mockito.when(processClusterRepository.findByCode(code)).thenReturn(Optional.of(cluster));
    service.update(data);
    Mockito.verify(processClusterRepository, Mockito.times(1)).findById(Mockito.any());
    Mockito.verify(processClusterRepository, Mockito.times(1)).findByCode(Mockito.any());
    Mockito.verify(processClusterRepository, Mockito.times(1)).save(Mockito.any());
  }
}
