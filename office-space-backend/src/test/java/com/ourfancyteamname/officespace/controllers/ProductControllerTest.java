package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

  @InjectMocks
  private ProductController controller;

  @Mock
  private ProductService productService;

  @Test
  public void findByPaging() {
    controller.findByPaging(null);
    Mockito.verify(productService, Mockito.times(1)).findByPaging(Mockito.any());
  }

  @Test
  public void create() {
    controller.create(null);
    Mockito.verify(productService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  public void update() {
    controller.update(null);
    Mockito.verify(productService, Mockito.times(1)).update(Mockito.any());
  }

  @Test
  public void delete() {
    controller.delete(null);
    Mockito.verify(productService, Mockito.times(1)).delete(Mockito.any());
  }

  @Test
  public void getListProductDisplayName() {
    controller.getListProductDisplayName(null);
    Mockito.verify(productService, Mockito.times(1)).findProductWithDisplayName(Mockito.any());
  }

}
