package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProductConverterTest {

  private final ProductConverter converter = Mappers.getMapper(ProductConverter.class);

  @Test
  void toDto() {
    Assertions.assertNull(converter.toDto(null));
    Assertions.assertNotNull(converter.toDto(new Product()));
  }

  @Test
  void toEntity() {
    Assertions.assertNull(converter.toEntity(null));
    Assertions.assertNotNull(converter.toEntity(new ProductDto()));
  }

  @Test
  void toDtoWithDisplayName() {
    Assertions.assertNull(converter.toDtoWithDisplayName(null));
    Assertions.assertEquals(String.join(" - ", "name", "partNumber"),
        converter.toDtoWithDisplayName(Product.builder()
            .name("name")
            .partNumber("partNumber")
            .build()).getDisplayName());
  }

}
