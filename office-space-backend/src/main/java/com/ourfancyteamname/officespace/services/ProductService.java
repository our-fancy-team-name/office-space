package com.ourfancyteamname.officespace.services;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

  Page<ProductDto> findAll(TableSearchRequest tableSearchRequest);

  Product create(ProductDto productDto);

  Product update(ProductDto productDto);

  void delete(Integer productId);
}
