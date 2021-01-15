package com.ourfancyteamname.officespace.services.impl;

import com.ourfancyteamname.officespace.db.converters.dtos.ProductConverter;
import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.db.repos.PackageRepository;
import com.ourfancyteamname.officespace.db.repos.ProductRepository;
import com.ourfancyteamname.officespace.db.repos.view.ProcessListViewRepository;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.CharConstants;
import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProductServiceImpl extends AbstractViewServiceImpl<Product, ProductRepository> implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProductConverter productConverter;

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private ProcessListViewRepository processListViewRepository;

  @Override
  public ProductRepository getExecutor() {
    return this.productRepository;
  }

  @Override
  public Page<ProductDto> findByPaging(TableSearchRequest tableSearchRequest) {
    return findAll(tableSearchRequest, productConverter::toDto);
  }

  @Override
  public Page<ProductDto> findProductWithDisplayName(TableSearchRequest tableSearchRequest) {
    return findAll(tableSearchRequest, productConverter::toDtoWithDisplayName);
  }

  @Override
  public Product create(ProductDto productDto) {
    Assert.isTrue(!productRepository.existsByName(productDto.getName()),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.NAME.name(), ErrorCode.DUPLICATED.name()));
    Assert.isTrue(!productRepository.existsByPartNumber(productDto.getPartNumber()),
        String.join(CharConstants.DELIMITER.getValue(), ErrorObject.PART_NUMBER.name(), ErrorCode.DUPLICATED.name()));
    return productRepository.save(productConverter.toEntity(productDto));
  }

  @Override
  public Product update(ProductDto productDto) {
    Product target = productRepository.findById(productDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND.name()));
    if (!productRepository.findByName(productDto.getName())
        .map(Product::getName)
        .map(name -> name.equals(target.getName()))
        .orElse(true)) {
      throw new IllegalArgumentException(
          String.join(CharConstants.DELIMITER.getValue(), ErrorObject.NAME.name(), ErrorCode.DUPLICATED.name()));
    }

    if (!productRepository.findByPartNumber(productDto.getPartNumber())
        .map(Product::getPartNumber)
        .map(partNumber -> partNumber.equals(target.getPartNumber()))
        .orElse(true)) {
      throw new IllegalArgumentException(
          String.join(CharConstants.DELIMITER.getValue(), ErrorObject.PART_NUMBER.name(), ErrorCode.DUPLICATED.name()));
    }

    if (!target.getClusterId().equals(productDto.getClusterId()) &&
        processListViewRepository.existsByProductIdAndClusterCurrentNotNull(productDto.getId())) {
      throw new IllegalArgumentException(
          String.join(CharConstants.DELIMITER.getValue(), ErrorObject.CLUSTER.name(), ErrorCode.IN_USE.name()));
    }

    target.setDescription(productDto.getDescription());
    target.setFamily(productDto.getFamily());
    target.setName(productDto.getName());
    target.setPartNumber(productDto.getPartNumber());
    target.setClusterId(productDto.getClusterId());
    return productRepository.save(target);
  }

  @Override
  public void delete(Integer productId) {
    Assert.isTrue(!packageRepository.existsByProductId(productId), ErrorCode.IN_USE.name());
    productRepository.deleteById(productId);
  }
}
