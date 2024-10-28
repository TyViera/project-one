package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public Product postClient(@RequestBody Product product) {
        productService.save(product);
        return product;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
        var product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateById(@PathVariable("id") Integer id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    
}
