package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProductConverter;
import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.ProductRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.ProductDto;
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
public class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl service;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private SpecificationService specificationService;

  @Mock
  private PaginationService paginationService;

  @Mock
  private SortingService sortingService;

  @Mock
  private ProductConverter productConverter;

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private ProcessListViewRepository processListViewRepository;

  @Test
  public void findALl() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationService.specificationBuilder(tableSearchRequest);
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), null);
    Page result = new PageImpl(Arrays.asList(Product.builder().build()));
    Mockito.when(productRepository.findAll(specs, pageable))
        .thenReturn(result);
    Page<ProductDto> processGeneralDtos = service.findAll(tableSearchRequest);
    Assert.assertEquals(1, processGeneralDtos.getTotalElements());
    Mockito.verify(productConverter, Mockito.times(1)).toDto(Mockito.any());
  }

  @Test
  public void findProductWithDisplayName() {
    TableSearchRequest tableSearchRequest = TableSearchRequest.builder().build();
    Specification specs = specificationService.specificationBuilder(tableSearchRequest);
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), null);
    Page result = new PageImpl(Arrays.asList(Product.builder().build()));
    Mockito.when(productRepository.findAll(specs, pageable))
        .thenReturn(result);
    Page<ProductDto> processGeneralDtos = service.findProductWithDisplayName(tableSearchRequest);
    Assert.assertEquals(1, processGeneralDtos.getTotalElements());
    Mockito.verify(productConverter, Mockito.times(1)).toDtoWithDisplayName(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void create_duplicatedName() {
    ProductDto productDto = ProductDto.builder().name("name").build();
    Mockito.when(productRepository.existsByName("name")).thenReturn(true);
    service.create(productDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void create_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.existsByPartNumber("partNumber")).thenReturn(true);
    service.create(productDto);
  }

  @Test
  public void create_success() {
    ProductDto productDto = ProductDto.builder().partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.existsByPartNumber("partNumber")).thenReturn(false);
    Mockito.when(productRepository.existsByName("name")).thenReturn(false);
    service.create(productDto);
    Mockito.verify(productConverter, Mockito.times(1)).toEntity(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_notFound() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber").name("name").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
    service.update(productDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_duplicatedName() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber1").name("name2").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name2")).thenReturn(Optional.of(product2));
    service.update(productDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_duplicatedPartNumber() {
    ProductDto productDto = ProductDto.builder().id(1).partNumber("partNumber2").name("name1").build();
    Product product1 = Product.builder().id(1).partNumber("partNumber1").name("name1").build();
    Product product2 = Product.builder().id(2).partNumber("partNumber2").name("name2").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber2")).thenReturn(Optional.of(product2));
    service.update(productDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void update_clusterInUse() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber1")).thenReturn(Optional.of(product1));
    Mockito.when(processListViewRepository.existsByProductIdAndClusterCurrentNotNull(1)).thenReturn(true);
    service.update(productDto);
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  public void update_success() {
    ProductDto productDto = ProductDto.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Product product1 = Product.builder().id(1).clusterId(1).partNumber("partNumber1").name("name1").build();
    Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByName("name1")).thenReturn(Optional.of(product1));
    Mockito.when(productRepository.findByPartNumber("partNumber1")).thenReturn(Optional.of(product1));
    service.update(productDto);
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test(expected = IllegalArgumentException.class)
  public void delete_inUse() {
    Mockito.when(packageRepository.existsByProductId(1)).thenReturn(true);
    service.delete(1);
  }

  @Test
  public void delete_success() {
    Mockito.when(packageRepository.existsByProductId(1)).thenReturn(false);
    service.delete(1);
    Mockito.verify(productRepository, Mockito.times(1)).deleteById(1);
  }

}
