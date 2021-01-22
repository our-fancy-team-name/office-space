package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.PackageConverter;
import com.ourfancyteamname.officespace.db.entities.Package;
import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.view.PackageListViewRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.PackageDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.CharConstants;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.enums.ErrorObject;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PackageServiceImplTest {

  @InjectMocks
  private PackageServiceImpl service;

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private PackageConverter packageConverter;

  @Mock
  private PackageListViewRepository packageListViewRepository;

  @Mock(name = "specificationBuilderService")
  private SpecificationBuilderServiceImpl<PackageListView> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderService")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderService")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Test
  void create() {
    String serialNumber = "serialNumber";
    Mockito.when(packageRepository.existsBySerialNumber(serialNumber)).thenReturn(true);
    var data = PackageDto.builder().serialNumber(serialNumber).build();
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(data), ErrorCode.DUPLICATED.name());
  }

  @Test
  void create_success() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().serialNumber(serialNumber).build();
    Mockito.when(packageRepository.existsBySerialNumber(serialNumber)).thenReturn(false);
    service.create(packageDto);
    Mockito.verify(packageConverter, Mockito.times(1)).toEntity(Mockito.any());
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void update_notFound() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.empty());
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(packageDto),
        ErrorCode.NOT_FOUND.name());
  }

  @Test
  void update_duplicate() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).serialNumber(serialNumber).build();
    Package dup = Package.builder().id(2).serialNumber("sn").build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(dup));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(packageDto),
        ErrorCode.DUPLICATED.name());
  }

  @Test
  void update() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(1).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(aPackage));
    service.update(packageDto);
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void update_clusterNotInUse() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(2).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(aPackage));
    Mockito.when(processListViewRepository.existsBySerialAndClusterCurrentNotNull(serialNumber))
        .thenReturn(false);
    service.update(packageDto);
    Mockito.verify(packageRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void update_clusterInUse() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(2).serialNumber(serialNumber).build();
    Mockito.when(packageRepository.findById(1)).thenReturn(Optional.of(aPackage));
    Mockito.when(packageRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.of(aPackage));
    Mockito.when(processListViewRepository.existsBySerialAndClusterCurrentNotNull(serialNumber))
        .thenReturn(true);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.update(packageDto),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.CLUSTER.name(), ErrorCode.IN_USE.name()));
  }

  @Test
  void delete() {
    service.delete(1);
    Mockito.verify(packageRepository, Mockito.times(1)).deleteById(1);
  }

  @Test
  void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification<PackageListView> specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    Sort sort = null;
    List<PackageListView> result = Collections.singletonList(PackageListView.builder().build());
    Mockito.when(paginationBuilderServiceImpl.from(tableSearchRequest)).thenReturn(Pageable.unpaged());
    Mockito.when(service.getExecutor().findAll(specs, sort)).thenReturn(result);
    Page<PackageListView> processGeneralDtos = service.getListView(tableSearchRequest);
    Assertions.assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
