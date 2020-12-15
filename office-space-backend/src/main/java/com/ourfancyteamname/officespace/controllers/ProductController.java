package com.ourfancyteamname.officespace.controllers;

import com.ourfancyteamname.officespace.annotations.CanEditProduct;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping("/list")
  public ResponseEntity<Page<ProductDto>> getUserRoleListView(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(productService.findAll(tableSearchRequest));
  }

  @PostMapping("/create")
  @CanEditProduct
  public ResponseEntity<Void> create(@RequestBody ProductDto productDto) {
    return ResponseEntity.ok().build();
  }

}
