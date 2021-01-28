package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.mockito.Mockito.any;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ourfancyteamname.officespace.services.ProductService;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ProductControllerTest {

  @InjectMocks
  private ProductController controller;

  @Mock
  private ProductService productService;

  @Test
  void findByPaging() {
    controller.findByPaging(null);
    verifyInvoke1Time(productService).findByPaging(any());
  }

  @Test
  void create() {
    controller.create(null);
    verifyInvoke1Time(productService).create(any());
  }

  @Test
  void update() {
    controller.update(null);
    verifyInvoke1Time(productService).update(any());
  }

  @Test
  void delete() {
    controller.delete(null);
    verifyInvoke1Time(productService).delete(any());
  }

  @Test
  void getListProductDisplayName() {
    controller.getListProductDisplayName(null);
    verifyInvoke1Time(productService).findProductWithDisplayName(any());
  }

}
