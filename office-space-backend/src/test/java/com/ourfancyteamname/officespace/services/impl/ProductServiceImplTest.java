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

import com.ourfancyteamname.officespace.db.converters.dtos.ProductConverter;
import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.ProductRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.db.services.impl.PaginationBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SortingBuilderServiceImpl;
import com.ourfancyteamname.officespace.db.services.impl.SpecificationBuilderServiceImpl;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl service;

  @Mock
  private ProductRepository productRepository;

  @Mock(name = "specificationBuilderServiceImpl")
  private SpecificationBuilderServiceImpl<Product> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderServiceImpl")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderServiceImpl")
  private SortingBuilderServiceImpl sortingBuilderServiceImpl;

  @Mock
  private ProductConverter productConverter;

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Test
  void findAll() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    List<Product> result = Collections.singletonList(Product.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), Pageable.unpaged());
    mockReturn(productRepository.findAll((Specification<Product>) null, (Sort) null), result);
    Page<ProductDto> processGeneralDtos = service.findByPaging(tableSearchRequest);
    assertEquals(1, processGeneralDtos.getTotalElements());
    verifyInvoke1Time(productConverter).toDto(any());
  }

  @Test
  void findProductWithDisplayName() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    List<Product> result = Collections.singletonList(Product.builder().build());
    mockReturn(paginationBuilderServiceImpl.from(tableSearchRequest), Pageable.unpaged());
    mockReturn(productRepository.findAll((Specification<Product>) null, (Sort) null), result);
    Page<ProductDto> processGeneralDtos = service.findProductWithDisplayName(tableSearchRequest);
    assertEquals(1, processGeneralDtos.getTotalElements());
    verifyInvoke1Time(productConverter).toDtoWithDisplayName(any());
  }

  @Test
  void create_duplicatedName() {
    ProductDto productDto = ProductDto.builder().name("name").build();
    mockReturn(productRepository.existsByName("name"), true);
    assertThrowIllegalDuplicated(() -> service.create(productDto));
  }

  @Test
  void create_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    mockReturn(productRepository.existsByPartNumber("partNumber"), true);
    assertThrowIllegalDuplicated(() -> service.create(productDto));
  }

  @Test
  void create_success() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    mockReturn(productRepository.existsByPartNumber("partNumber"), false);
    mockReturn(productRepository.existsByName("name"), false);
    service.create(productDto);
    verifyInvoke1Time(productConverter).toEntity(any());
  }

  @Test
  void update_notFound() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber").name("name").build();
    mockReturn(productRepository.findById(1), Optional.empty());
    assertThrowIllegalNotFound(() -> service.update(productDto));
  }

  @Test
  void update_duplicatedName() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber1").name("name2").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    mockReturn(productRepository.findById(1), Optional.of(product1));
    mockReturn(productRepository.findByName("name2"), Optional.of(product2));
    assertObjectDuplicated(() -> service.update(productDto), ErrorObject.NAME.name());
  }

  @Test
  void update_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber2").name("name1").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    mockReturn(productRepository.findById(1), Optional.of(product1));
    mockReturn(productRepository.findByName("name1"), Optional.of(product1));
    mockReturn(productRepository.findByPartNumber("partNumber2"), Optional.of(product2));
    assertObjectDuplicated(() -> service.update(productDto), ErrorObject.PART_NUMBER.name());
  }

  @Test
  void update_clusterInUse() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(2).partNumber("partNumber1").name("name1").build();
    mockReturn(productRepository.findById(1), Optional.of(product1));
    mockReturn(productRepository.findByName("name1"), Optional.of(product1));
    mockReturn(productRepository.findByPartNumber("partNumber1"), Optional.of(product1));
    mockReturn(processListViewRepository.existsByProductIdAndClusterCurrentNotNull(1), true);
    assertObjectInUse(() -> service.update(productDto), ErrorObject.CLUSTER.name());
  }

  @Test
  void update_cluster_notInUse() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(2).partNumber("partNumber1").name("name1").build();
    mockReturn(productRepository.findById(1), Optional.of(product1));
    mockReturn(productRepository.findByName("name1"), Optional.of(product1));
    mockReturn(productRepository.findByPartNumber("partNumber1"), Optional.of(product1));
    mockReturn(processListViewRepository.existsByProductIdAndClusterCurrentNotNull(1), false);
    service.update(productDto);
    verifyInvoke1Time(productRepository).save(any());
  }

  @Test
  void update_success() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    mockReturn(productRepository.findById(1), Optional.of(product1));
    mockReturn(productRepository.findByName("name1"), Optional.of(product1));
    mockReturn(productRepository.findByPartNumber("partNumber1"), Optional.of(product1));
    service.update(productDto);
    verifyInvoke1Time(productRepository).save(any());
  }

  @Test
  void delete_inUse() {
    mockReturn(packageRepository.existsByProductId(1), true);
    assertThrowIllegalInUse(() -> service.delete(1));
  }

  @Test
  void delete_success() {
    mockReturn(packageRepository.existsByProductId(1), false);
    service.delete(1);
    verifyInvoke1Time(productRepository).deleteById(1);
  }

}
