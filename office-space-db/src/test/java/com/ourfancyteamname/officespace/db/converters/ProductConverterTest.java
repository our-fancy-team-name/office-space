package com.ourfancyteamname.officespace.db.converters;

import com.ourfancyteamname.officespace.db.converters.dtos.ProductConverter;
import com.ourfancyteamname.officespace.db.entities.Product;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductConverterTest {

  private ProductConverter converter = Mappers.getMapper(ProductConverter.class);

  @Test
  public void toDto() {
    Assert.assertNull(converter.toDto(null));
    Assert.assertNotNull(converter.toDto(new Product()));
  }

  @Test
  public void toEntity() {
    Assert.assertNull(converter.toEntity(null));
    Assert.assertNotNull(converter.toEntity(new ProductDto()));
  }

  @Test
  public void toDtoWithDisplayName() {
    Assert.assertNull(converter.toDtoWithDisplayName(null));
    Assert.assertEquals(String.join(" - ", "name", "partNumber"),
        converter.toDtoWithDisplayName(Product.builder()
            .name("name")
            .partNumber("partNumber")
            .build()).getDisplayName());
  }

}
