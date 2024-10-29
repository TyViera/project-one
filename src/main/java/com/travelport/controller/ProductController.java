package com.travelport.controller;

import com.travelport.entities.Client;
import com.travelport.entities.Product;
import com.travelport.jpa.ProductDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productDao.list();
    }

    @GetMapping("{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable("code") Integer code) {
        var product = productDao.findById(code);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product) {
        productDao.save(product);
        return product;
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable("code") Integer code, @RequestBody Product product) {
        var findProduct = productDao.findById(code);
        if (findProduct.isPresent()) {
            findProduct.get().setName(product.getName());
            productDao.update(findProduct.get());
            return ResponseEntity.ok(findProduct.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("code") Integer code) {
        productDao.delete(code);
        return ResponseEntity.noContent().build();
    }

}
