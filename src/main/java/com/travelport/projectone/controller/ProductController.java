package com.travelport.projectone.controller;

import com.travelport.projectone.entity.Product;
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

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product product) {
        var savedProduct = productService.save(product);
        if (savedProduct.isEmpty()) return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.ok(savedProduct.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        var updatedProduct = productService.update(id, product);
        if (updatedProduct.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedProduct.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") Integer id) {
        var isProductDeleted = productService.delete(id);
        if (!isProductDeleted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Product> get(@RequestParam(value = "orderBy", required = false) String orderBy) {
        return productService.findAll(orderBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Integer id) {
        var foundProduct = productService.findById(id);
        if (foundProduct.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(foundProduct.get());
    }
}
