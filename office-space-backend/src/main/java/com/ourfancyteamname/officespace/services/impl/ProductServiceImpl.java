package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProductConverter;
import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.db.repos.ProductRepository;
import com.ourfancyteamname.officespace.db.services.PaginationService;
import com.ourfancyteamname.officespace.db.services.SortingService;
import com.ourfancyteamname.officespace.db.services.SpecificationService;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProductServiceImpl implements ProductService {

  private static final String DELIMITER = "_";

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SpecificationService specificationService;

  @Autowired
  private PaginationService paginationService;

  @Autowired
  private SortingService sortingService;

  @Autowired
  private ProductConverter productConverter;

  @Override
  public Page<ProductDto> findAll(TableSearchRequest tableSearchRequest) {
    Specification<Product> productSpecification = specificationService.specificationBuilder(tableSearchRequest);
    Sort sort = sortingService.getSort(tableSearchRequest.getSortingRequest());
    Pageable pageable = paginationService.getPage(tableSearchRequest.getPagingRequest(), sort);
    return productRepository.findAll(productSpecification, pageable)
        .map(productConverter::toDto);
  }

  @Override
  public Product create(ProductDto productDto) {
    Assert.isTrue(!productRepository.existsByName(productDto.getName()),
        String.join(DELIMITER, ErrorObject.NAME.name(), ErrorCode.DUPLICATED.name()));
    Assert.isTrue(!productRepository.existsByPartNumber(productDto.getPartNumber()),
        String.join(DELIMITER, ErrorObject.PART_NUMBER.name(), ErrorCode.DUPLICATED.name()));
    return productRepository.save(productConverter.toEntity(productDto));
  }
}
