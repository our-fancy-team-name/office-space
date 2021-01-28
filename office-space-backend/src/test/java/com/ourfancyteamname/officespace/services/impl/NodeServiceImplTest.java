package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.db.repos.ProcessNodeRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;
import com.ourfancyteamname.officespace.test.services.AssertionHelper;

@UnitTest
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
    mockReturn(processNodeRepository.existsByCode("code"), true);
    var data = ProcessGeneralDto.builder().code("code").build();
    AssertionHelper.assertThrowIllegal(() -> service.create(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void create_success() {
    mockReturn(processNodeRepository.existsByCode("code"), false);
    service.create(ProcessGeneralDto.builder().code("code").build());
    verifyInvoke1Time(processGeneralConverter).fromDtoToNodeEntity(any());
    verifyInvoke1Time(processNodeRepository).save(any());
  }

  @Test
  void update_notFound() {
    mockReturn(processNodeRepository.findById(1), Optional.empty());
    var data = ProcessGeneralDto.builder().id(1).code("code").build();
    AssertionHelper.assertThrowIllegal(() -> service.update(data), ErrorCode.NOT_FOUND.name());
  }

  @Test
  void update_duplicated() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    ProcessNode processNode2 = ProcessNode.builder().id(2).code("code2").build();
    mockReturn(processNodeRepository.findById(1), Optional.of(processNode));
    mockReturn(processNodeRepository.findByCode("code2"), Optional.of(processNode2));
    final var data = ProcessGeneralDto.builder().id(1).code("code2").build();
    AssertionHelper.assertThrowIllegal(() -> service.update(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void update() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    mockReturn(processNodeRepository.findById(1), Optional.of(processNode));
    mockReturn(processNodeRepository.findByCode(code), Optional.of(processNode));
    service.update(ProcessGeneralDto.builder().id(1).code(code).build());
    verifyInvoke1Time(processNodeRepository).save(any());
  }

  @Test
  void getListView() {
    var tableSearchRequest = TableSearchRequest.builder().build();
    var specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    List<ProcessNode> result = Collections.singletonList(ProcessNode.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), Pageable.unpaged());
    mockReturn(service.getExecutor().findAll(specs, (Sort) null), result);
    Page<ProcessGeneralDto> processGeneralDtos = service.getListView(tableSearchRequest);
    assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
