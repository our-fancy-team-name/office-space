package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @InjectMocks
  private ProductController controller;

  @Mock
  private ProductService productService;

  @Test
  void findByPaging() {
    controller.findByPaging(null);
    Mockito.verify(productService, Mockito.times(1)).findByPaging(Mockito.any());
  }

  @Test
  void create() {
    controller.create(null);
    Mockito.verify(productService, Mockito.times(1)).create(Mockito.any());
  }

  @Test
  void update() {
    controller.update(null);
    Mockito.verify(productService, Mockito.times(1)).update(Mockito.any());
  }

  @Test
  void delete() {
    controller.delete(null);
    Mockito.verify(productService, Mockito.times(1)).delete(Mockito.any());
  }

  @Test
  void getListProductDisplayName() {
    controller.getListProductDisplayName(null);
    Mockito.verify(productService, Mockito.times(1)).findProductWithDisplayName(Mockito.any());
  }

}
