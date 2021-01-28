package com.ourfancyteamname.officespace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.annotations.CanEditProduct;
import com.ourfancyteamname.officespace.dtos.ProductDto;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping("/list")
  public ResponseEntity<Page<ProductDto>> findByPaging(@RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(productService.findByPaging(tableSearchRequest));
  }

  @PostMapping("/create")
  @Transactional
  @CanEditProduct
  public ResponseEntity<Void> create(@RequestBody ProductDto productDto) {
    productService.create(productDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/update")
  @Transactional
  @CanEditProduct
  public ResponseEntity<Void> update(@RequestBody ProductDto productDto) {
    productService.update(productDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  @CanEditProduct
  public ResponseEntity<Void> delete(@PathVariable("id") Integer productId) {
    productService.delete(productId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/list-name")
  public ResponseEntity<Page<ProductDto>> getListProductDisplayName(
      @RequestBody TableSearchRequest tableSearchRequest) {
    return ResponseEntity.ok(productService.findProductWithDisplayName(tableSearchRequest));
  }

}
