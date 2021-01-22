package com.ourfancyteamname.officespace.services.impl;

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
class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl service;

  @Mock
  private ProductRepository productRepository;

  @Mock(name = "specificationBuilderService")
  private SpecificationBuilderServiceImpl<Product> specificationBuilderServiceImpl;

  @Mock(name = "paginationBuilderService")
  private PaginationBuilderServiceImpl paginationBuilderServiceImpl;

  @Mock(name = "sortingBuilderService")
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
    Mockito.when(paginationBuilderServiceImpl.from(tableSearchRequest))
        .thenReturn(Pageable.unpaged());
    Mockito.when(productRepository.findAll((Specification<Product>) null, (Sort) null))
        .thenReturn(result);
    Page<ProductDto> processGeneralDtos = service.findByPaging(tableSearchRequest);
    Assertions.assertEquals(1, processGeneralDtos.getTotalElements());
    Mockito.verify(productConverter, Mockito.times(1)).toDto(Mockito.any());
  }

  @Test
  void findProductWithDisplayName() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    List<Product> result = Collections.singletonList(Product.builder().build());
    Mockito.when(paginationBuilderServiceImpl.from(tableSearchRequest)).thenReturn(Pageable.unpaged());
    Mockito.when(productRepository.findAll((Specification<Product>) null, (Sort) null)).thenReturn(result);
    Page<ProductDto> processGeneralDtos = service.findProductWithDisplayName(tableSearchRequest);
    Assertions.assertEquals(1, processGeneralDtos.getTotalElements());
    Mockito.verify(productConverter, Mockito.times(1)).toDtoWithDisplayName(Mockito.any());
  }

  @Test
  void create_duplicatedName() {
    ProductDto productDto = ProductDto.builder().name("name").build();
    Mockito.when(productRepository.existsByName("name")).thenReturn(true);
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.create(productDto),
        ErrorCode.DUPLICATED.name()
    );
  }

  @Test
  void create_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.existsByPartNumber("partNumber")).thenReturn(true);
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.create(productDto),
        ErrorCode.DUPLICATED.name()
    );
  }

  @Test
  void create_success() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.existsByPartNumber("partNumber")).thenReturn(false);
    Mockito.when(productRepository.existsByName("name")).thenReturn(false);
    service.create(productDto);
    Mockito.verify(productConverter, Mockito.times(1)).toEntity(Mockito.any());
  }

  @Test
  void update_notFound() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(productDto),
        ErrorCode.NOT_FOUND.name()
    );
  }

  @Test
  void update_duplicatedName() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber1").name("name2").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name2")).thenReturn(Optional.of(product2));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(productDto),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.NAME.name(), ErrorCode.DUPLICATED.name())
    );
  }

  @Test
  void update_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber2").name("name1").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber2")).thenReturn(Optional.of(product2));
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(productDto),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.PART_NUMBER.name(), ErrorCode.DUPLICATED.name())
    );
  }

  @Test
  void update_clusterInUse() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(2).partNumber("partNumber1").name("name1").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber1")).thenReturn(Optional.of(product1));
    Mockito.when(processListViewRepository.existsByProductIdAndClusterCurrentNotNull(1)).thenReturn(true);
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> service.update(productDto),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.CLUSTER.name(), ErrorCode.IN_USE.name())
    );
  }

  @Test
  void update_cluster_notInUse() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(2).partNumber("partNumber1").name("name1").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber1")).thenReturn(Optional.of(product1));
    Mockito.when(processListViewRepository.existsByProductIdAndClusterCurrentNotNull(1)).thenReturn(false);
    service.update(productDto);
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void update_success() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber1")).thenReturn(Optional.of(product1));
    service.update(productDto);
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  void delete_inUse() {
    Mockito.when(packageRepository.existsByProductId(1)).thenReturn(true);
    Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(1), ErrorCode.IN_USE.name());
  }

  @Test
  void delete_success() {
    Mockito.when(packageRepository.existsByProductId(1)).thenReturn(false);
    service.delete(1);
    Mockito.verify(productRepository, Mockito.times(1)).deleteById(1);
  }

}
