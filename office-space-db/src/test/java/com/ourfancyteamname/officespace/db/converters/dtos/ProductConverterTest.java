package com.ourfancyteamname.officespace.db.converters.dtos;

import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ProductConverterTest {

  private ProductConverter converter = Mappers.getMapper(ProductConverter.class);

  @Test
  public void toDto() {
    Assertions.assertNull(converter.toDto(null));
    Assertions.assertNotNull(converter.toDto(new Product()));
  }

  @Test
  public void toEntity() {
    Assertions.assertNull(converter.toEntity(null));
    Assertions.assertNotNull(converter.toEntity(new ProductDto()));
  }

  @Test
  public void toDtoWithDisplayName() {
    Assertions.assertNull(converter.toDtoWithDisplayName(null));
    Assertions.assertEquals(String.join(" - ", "name", "partNumber"),
        converter.toDtoWithDisplayName(Product.builder()
            .name("name")
            .partNumber("partNumber")
            .build()).getDisplayName());
  }

}
