package com.ourfancyteamname.officespace.db.converters.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProductConverterTest {

  private final ProductConverter converter = Mappers.getMapper(ProductConverter.class);

  @Test
  void toDto() {
    assertNull(converter.toDto(null));
    assertNotNull(converter.toDto(new Product()));
  }

  @Test
  void toEntity() {
    assertNull(converter.toEntity(null));
    assertNotNull(converter.toEntity(new ProductDto()));
  }

  @Test
  void toDtoWithDisplayName() {
    assertNull(converter.toDtoWithDisplayName(null));
    assertEquals(String.join(" - ", "name", "partNumber"),
        converter.toDtoWithDisplayName(Product.builder()
            .name("name")
            .partNumber("partNumber")
            .build()).getDisplayName());
  }

}
