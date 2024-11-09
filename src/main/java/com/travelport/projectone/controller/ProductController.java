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

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProduct();
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product) { return productService.save(product); }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductByNif(@PathVariable("code") Integer code) {
        var product = productService.findByCode(code);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable("code") Integer code, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(code, product));
    }

    @DeleteMapping
    public ResponseEntity<Product> deleteProduct(@RequestParam("code") Integer code) {
        productService.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/income-report")
    public List<Product> incomeRepost() {
        return productService.incomeReport();
    }
}
