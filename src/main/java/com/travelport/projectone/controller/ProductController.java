package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable int id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
    return ResponseEntity.ok(productService.updateProduct(id, product));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
