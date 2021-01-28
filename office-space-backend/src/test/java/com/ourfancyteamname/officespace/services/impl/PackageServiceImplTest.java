package com.ourfancyteamname.officespace.services.impl;

import static com.ourfancyteamname.officespace.test.services.AssertionHelper.*;
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
import org.springframework.data.jpa.domain.Specification;

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
import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PackageServiceImplTest {

  @InjectMocks
  private PackageServiceImpl service;

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private PackageConverter packageConverter;

  @Mock
  private PackageListViewRepository packageListViewRepository;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<PackageListView> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Test
  void create() {
    String serialNumber = "serialNumber";
    mockReturn(packageRepository.existsBySerialNumber(serialNumber), true);
    var data = PackageDto.builder().serialNumber(serialNumber).build();
    assertThrowIllegalDuplicated(() -> service.create(data));
  }

  @Test
  void create_success() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().serialNumber(serialNumber).build();
    mockReturn(packageRepository.existsBySerialNumber(serialNumber), false);
    service.create(packageDto);
    verifyInvoke1Time(packageConverter).toEntity(any());
    verifyInvoke1Time(packageRepository).save(any());
  }

  @Test
  void update_notFound() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    mockReturn(packageRepository.findById(1), Optional.empty());
    assertThrowIllegalNotFound(() -> service.update(packageDto));
  }

  @Test
  void update_duplicate() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).serialNumber(serialNumber).build();
    Package dup = Package.builder().id(2).serialNumber("sn").build();
    mockReturn(packageRepository.findById(1), Optional.of(aPackage));
    mockReturn(packageRepository.findBySerialNumber(serialNumber), Optional.of(dup));
    assertThrowIllegalDuplicated(() -> service.update(packageDto));
  }

  @Test
  void update() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(1).serialNumber(serialNumber).build();
    mockReturn(packageRepository.findById(1), Optional.of(aPackage));
    mockReturn(packageRepository.findBySerialNumber(serialNumber), Optional.of(aPackage));
    service.update(packageDto);
    verifyInvoke1Time(packageRepository).save(any());
  }

  @Test
  void update_clusterNotInUse() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(2).serialNumber(serialNumber).build();
    mockReturn(packageRepository.findById(1), Optional.of(aPackage));
    mockReturn(packageRepository.findBySerialNumber(serialNumber), Optional.of(aPackage));
    mockReturn(processListViewRepository.existsBySerialAndClusterCurrentNotNull(serialNumber),
        false);
    service.update(packageDto);
    verifyInvoke1Time(packageRepository).save(any());
  }

  @Test
  void update_clusterInUse() {
    String serialNumber = "serialNumber";
    PackageDto packageDto = PackageDto.builder().productId(1).id(1).serialNumber(serialNumber).build();
    Package aPackage = Package.builder().id(1).productId(2).serialNumber(serialNumber).build();
    mockReturn(packageRepository.findById(1), Optional.of(aPackage));
    mockReturn(packageRepository.findBySerialNumber(serialNumber), Optional.of(aPackage));
    mockReturn(processListViewRepository.existsBySerialAndClusterCurrentNotNull(serialNumber), true);
    assertObjectInUse(() -> service.update(packageDto), ErrorObject.CLUSTER.name());
  }

  @Test
  void delete() {
    service.delete(1);
    verifyInvoke1Time(packageRepository).deleteById(1);
  }

  @Test
  void getListView() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification<PackageListView> specs = specificationBuilderServiceImpl.from(tableSearchRequest);
    Sort sort = null;
    List<PackageListView> result = Collections.singletonList(PackageListView.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), Pageable.unpaged());
    mockReturn(service.getExecutor().findAll(specs, sort), result);
    Page<PackageListView> processGeneralDtos = service.getListView(tableSearchRequest);
    assertEquals(1, processGeneralDtos.getTotalElements());
  }
}
