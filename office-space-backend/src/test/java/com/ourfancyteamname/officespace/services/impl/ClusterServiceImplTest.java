package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;
import com.ourfancyteamname.officespace.test.services.AssertionHelper;

@UnitTest
class ClusterServiceImplTest {

  private static final String code = "code";

  @InjectMocks
  private ClusterServiceImpl service;

  @Mock
  private ProcessClusterRepository processClusterRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<ProcessCluster> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Test
  void create_duplicatedCode() {
    mockReturn(processClusterRepository.existsByCode(code), true);
    var dto = ProcessGeneralDto.builder().code(code).build();
    AssertionHelper.assertThrowIllegal(() -> service.create(dto), ErrorCode.DUPLICATED.name());
  }

  @Test
  void create_success() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().code(code).build();
    mockReturn(processClusterRepository.existsByCode(code), false);
    ProcessCluster result = processGeneralConverter.fromDtoToClusterEntity(data);
    mockReturn(processClusterRepository.save(result), result);
    service.create(data);
    verifyInvoke1Time(processClusterRepository).existsByCode(any());
    verifyInvoke1Time(processClusterRepository).save(any());
  }

  @Test
  void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification<ProcessCluster> specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    Pageable page = PageRequest.of(1, 2);
    Page<ProcessCluster> result =
        new PageImpl<>(Collections.singletonList(ProcessCluster.builder().code(code).build()));
    when(paginationBuilderServiceImpl.from(tableSearchRequest)).thenReturn(page);
    when(service.getExecutor().findAll(specs, page)).thenReturn(result);
    Page<ProcessGeneralDto> processGeneralDtos = service.getListView(tableSearchRequest);
    assertEquals(1, processGeneralDtos.getTotalElements());
  }

  @Test
  void update_notFound() {
    var data = ProcessGeneralDto.builder().id(1).code(code).build();
    mockReturn(processClusterRepository.findById(1), Optional.empty());
    AssertionHelper.assertThrowIllegal(() -> service.update(data), ErrorCode.NOT_FOUND.name());
  }

  @Test
  void update_duplicate() {
    var data = ProcessGeneralDto.builder().id(1).code(code).build();
    var dupData = ProcessCluster.builder().id(2).code(code).build();
    var cluster = ProcessCluster.builder().build();
    mockReturn(processClusterRepository.findById(1), Optional.of(cluster));
    mockReturn(processClusterRepository.findByCode(code), Optional.of(dupData));
    AssertionHelper.assertThrowIllegal(() -> service.update(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void update() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().id(1).code(code).build();
    ProcessCluster cluster = ProcessCluster.builder().build();
    mockReturn(processClusterRepository.findById(1), Optional.of(cluster));
    mockReturn(processClusterRepository.findByCode(code), Optional.of(cluster));
    service.update(data);
    verifyInvoke1Time(processClusterRepository).findById(any());
    verifyInvoke1Time(processClusterRepository).findByCode(any());
    verifyInvoke1Time(processClusterRepository).save(any());
  }
}
