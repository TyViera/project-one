package com.travelport.controller;

import com.travelport.entities.Product;
import com.travelport.service.ProductService;
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
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable("code") Integer code) {
        var product = productService.findProductById(code);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return product;
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable("code") Integer code, @RequestBody Product product) {
        var findProduct = productService.findProductById(code);
        if (findProduct.isPresent()) {
            findProduct.get().setName(product.getName());
            productService.updateProduct(findProduct.get());
            return ResponseEntity.ok(findProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("code") Integer code) {
        productService.deleteProduct(code);
        return ResponseEntity.noContent().build();
    }

}
