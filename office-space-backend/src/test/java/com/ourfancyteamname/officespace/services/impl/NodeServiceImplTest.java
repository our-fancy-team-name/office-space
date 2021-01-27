package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.db.repos.ProcessNodeRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NodeServiceImplTest {

  @InjectMocks
  private NodeServiceImpl service;

  @Mock
  private ProcessNodeRepository processNodeRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<ProcessNode> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Test
  void create_dup() {
    Mockito.when(processNodeRepository.existsByCode("code")).thenReturn(true);
    var data = ProcessGeneralDto.builder().code("code").build();
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void create_success() {
    Mockito.when(processNodeRepository.existsByCode("code")).thenReturn(false);
    service.create(ProcessGeneralDto.builder().code("code").build());
    Mockito.verify(processGeneralConverter, Mockito.times(1)).fromDtoToNodeEntity(Mockito.any());
    Mockito.verify(processNodeRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void update_notFound() {
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.empty());
    var data = ProcessGeneralDto.builder().id(1).code("code").build();
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(data), ErrorCode.NOT_FOUND.name());
  }

  @Test
  void update_duplicated() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    ProcessNode processNode2 = ProcessNode.builder().id(2).code("code2").build();
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.of(processNode));
    Mockito.when(processNodeRepository.findByCode("code2")).thenReturn(Optional.of(processNode2));
    final var data = ProcessGeneralDto.builder().id(1).code("code2").build();
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void update() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.of(processNode));
    Mockito.when(processNodeRepository.findByCode(code)).thenReturn(Optional.of(processNode));
    service.update(ProcessGeneralDto.builder().id(1).code(code).build());
    Mockito.verify(processNodeRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void getListView() {
    var tableSearchRequest = TableSearchRequest.builder().build();
    var specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    List<ProcessNode> result = Collections.singletonList(ProcessNode.builder().build());
    Mockito.when(paginationBuilderServiceImpl.from(tableSearchRequest)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, (Sort) null)).thenReturn(result);
    Page<ProcessGeneralDto> processGeneralDtos = service.getListView(tableSearchRequest);
    Assertions.assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
