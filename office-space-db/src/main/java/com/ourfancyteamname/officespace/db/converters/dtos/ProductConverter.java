package com.ourfancyteamname.officespace.db.converters.dtos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;

@Mapper
public interface ProductConverter {

  ProductDto toDto(Product product);

  Product toEntity(ProductDto productDto);

  @Mapping(
      expression = "java(String.join(\" - \", product.getName(), product.getPartNumber()))",
      target = "displayName"
  )
  ProductDto toDtoWithDisplayName(Product product);

}
