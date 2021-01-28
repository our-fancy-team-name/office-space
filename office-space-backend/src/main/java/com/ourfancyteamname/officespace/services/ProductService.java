package com.ourfancyteamname.officespace.services;

import org.springframework.data.domain.Page;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;

public interface ProductService {

  Page<ProductDto> findByPaging(TableSearchRequest tableSearchRequest);

  Page<ProductDto> findProductWithDisplayName(TableSearchRequest tableSearchRequest);

  Product create(ProductDto productDto);

  Product update(ProductDto productDto);

  void delete(Integer productId);
}
