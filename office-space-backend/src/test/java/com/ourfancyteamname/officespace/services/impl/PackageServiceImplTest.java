package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PackageConverter;
import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.view.PackageListViewRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.PackageDto;
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
public class PackageServiceImplTest {

  @InjectMocks
  private PackageServiceImpl service;

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private PackageConverter packageConverter;

  @Mock
  private PackageListViewRepository packageListViewRepository;

  @Mock
  private SpecificationService specificationService;

  @Mock
  private PaginationService paginationService;

  @Mock
  private SortingService sortingService;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Test(expected = IllegalArgumentException.class)
  public void create() {
    String serialNumber = "serialNumber";
    Mockito.when(packageRepository.existsBySerialNumber(serialNumber)).thenReturn(true);
    service.create(PackageDto.builder().serialNumber(serialNumber).build());
  }

  @Test
  public void create_success() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().serialNumber(serialNumber).build();
    Mockito.when(packageRepository.existsBySerialNumber(serialNumber)).thenReturn(false);
    service.create(packageDto);
    Mockito.verify(packageConverter, Mockito.times(1)).toEntity(Mockito.any());
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_notFound() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.empty());
    service.update(packageDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_duplicate() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).serialNumber(serialNumber).build();
    Package dup = Package.builder().id(2).serialNumber("sn").build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(dup));
    service.update(packageDto);
  }

  @Test
  public void update() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(1).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(aPackage));
    service.update(packageDto);
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_clusterInuse() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(2).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(aPackage));
    Mockito.when(processListViewRepository.existsBySerialAndClusterCurrentNotNull(serialNumber))
        .thenReturn(true);
    service.update(packageDto);
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void delete() {
    service.delete(1);
    Mockito.verify(packageRepository, Mockito.times(1)).deleteById(1);
  }

  @Test
  public void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationService.specificationBuilder(tableSearchRequest);
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), null);
    Page result = new PageImpl(Arrays.asList(PackageListView.builder().build()));
    Mockito.when(packageListViewRepository.findAll(specs, pageable))
        .thenReturn(result);
    Page<PackageListView> processGeneralDtos = service.getListView(tableSearchRequest);
    Assert.assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
