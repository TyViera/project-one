package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getProduct() {
        return productDao.list();
    }

    @PostMapping
    public Product postClient(@RequestBody Product product) {
        productDao.save(product);
        return product;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductByNif(@PathVariable("code") int code) {
        var product = productDao.getProductByCode(code);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Product> getProductByNif(@PathVariable("code") int code, @RequestBody Product product) {
        var findClient = productDao.getProductByCode(code);
        if (findClient.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        if (product.getName() != null && !product.getName().isEmpty()) {
            findClient.get().setName(product.getName());
        }
        productDao.update(findClient.get());
        return ResponseEntity.ok(findClient.get());
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("code") int code) {
        var product = productDao.deleteByCode(code);
        return product.map(u -> ResponseEntity.noContent().<Void>build()).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
