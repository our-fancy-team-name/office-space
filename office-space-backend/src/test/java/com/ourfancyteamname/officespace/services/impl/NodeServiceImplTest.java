package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import com.ourfancyteamname.officespace.db.repos.ProcessNodeRepository;
import com.ourfancyteamname.officespace.db.services.PaginationBuilderService;
import com.ourfancyteamname.officespace.db.services.SortingBuilderService;
import com.ourfancyteamname.officespace.db.services.SpecificationBuilderService;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NodeServiceImplTest {

  @InjectMocks
  private NodeServiceImpl service;

  @Mock
  private ProcessNodeRepository processNodeRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock
  private SpecificationBuilderService specificationBuilderService;

  @Mock
  private PaginationBuilderService paginationBuilderService;

  @Mock
  private SortingBuilderService sortingBuilderService;

  @Test(expected = IllegalArgumentException.class)
  public void create_dup() {
    Mockito.when(processNodeRepository.existsByCode("code")).thenReturn(true);
    service.create(ProcessGeneralDto.builder().code("code").build());
  }

  @Test
  public void create_success() {
    Mockito.when(processNodeRepository.existsByCode("code")).thenReturn(false);
    service.create(ProcessGeneralDto.builder().code("code").build());
    Mockito.verify(processGeneralConverter, Mockito.times(1)).fromDtoToNodeEntity(Mockito.any());
    Mockito.verify(processNodeRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_notFound() {
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.empty());
    service.update(ProcessGeneralDto.builder().id(1).code("code").build());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_duplicated() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    ProcessNode processNode2 = ProcessNode.builder().id(2).code("code2").build();
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.of(processNode));
    Mockito.when(processNodeRepository.findByCode("code2")).thenReturn(Optional.of(processNode2));
    service.update(ProcessGeneralDto.builder().id(1).code("code2").build());
  }

  @Test
  public void update() {
    String code = "code";
    ProcessNode processNode = ProcessNode.builder().id(1).code(code).build();
    Mockito.when(processNodeRepository.findById(1)).thenReturn(Optional.of(processNode));
    Mockito.when(processNodeRepository.findByCode(code)).thenReturn(Optional.of(processNode));
    service.update(ProcessGeneralDto.builder().id(1).code(code).build());
    Mockito.verify(processNodeRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationBuilderService.from(tableSearchRequest);
    Sort sort = null;
    List<ProcessNode> result = Arrays.asList(ProcessNode.builder().build());
    Mockito.when(paginationBuilderService.from(null, null)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, sort)).thenReturn(result);
    Page<ProcessGeneralDto> processGeneralDtos = service.getListView(tableSearchRequest);
    Assert.assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
