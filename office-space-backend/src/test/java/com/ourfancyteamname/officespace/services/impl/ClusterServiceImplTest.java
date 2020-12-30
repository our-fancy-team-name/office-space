package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProcessGeneralConverter;
import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import com.ourfancyteamname.officespace.db.repos.ProcessClusterRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ClusterServiceImplTest {

  private static final String code = "code";

  @InjectMocks
  private ClusterServiceImpl clusterService;

  @Mock
  private ProcessClusterRepository processClusterRepository;

  @Mock
  private ProcessGeneralConverter processGeneralConverter;

  @Mock
  private SpecificationService specificationService;

  @Mock
  private PaginationService paginationService;

  @Mock
  private SortingService sortingService;

  @Test(expected = IllegalArgumentException.class)
  public void create_duplicatedCode() {
    Mockito.when(processClusterRepository.existsByCode(code)).thenReturn(true);
    clusterService.create(ProcessGeneralDto.builder().code(code).build());
  }

  @Test
  public void create_success() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().code(code).build();
    Mockito.when(processClusterRepository.existsByCode(code)).thenReturn(false);
    ProcessCluster result = processGeneralConverter.fromDtoToClusterEntity(data);
    Mockito.when(processClusterRepository.save(result)).thenReturn(result);
    clusterService.create(data);
  }

  @Test
  public void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationService.specificationBuilder(tableSearchRequest);
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), null);
    Page result = new PageImpl(Arrays.asList(ProcessCluster.builder().code(code).build()));
    Mockito.when(processClusterRepository.findAll(specs, pageable))
        .thenReturn(result);
    Page<ProcessGeneralDto> processGeneralDtos = clusterService.getListView(tableSearchRequest);
    Assert.assertEquals(1, processGeneralDtos.getTotalElements());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_notFound() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().id(1).code(code).build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.empty());
    clusterService.update(data);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_duplicate() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().id(1).code(code).build();
    ProcessCluster dupData = ProcessCluster.builder().id(2).code(code).build();
    ProcessCluster cluster = ProcessCluster.builder().build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.of(cluster));
    Mockito.when(processClusterRepository.findByCode(code)).thenReturn(Optional.of(dupData));
    clusterService.update(data);
  }

  @Test
  public void update() {
    ProcessGeneralDto data = ProcessGeneralDto.builder().id(1).code(code).build();
    ProcessCluster cluster = ProcessCluster.builder().build();
    Mockito.when(processClusterRepository.findById(1)).thenReturn(Optional.of(cluster));
    Mockito.when(processClusterRepository.findByCode(code)).thenReturn(Optional.of(cluster));
    clusterService.update(data);
  }
}
