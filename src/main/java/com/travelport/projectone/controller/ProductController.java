package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
public class ProductController  {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer id){
        var  product= productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product){
        return productService.save(product);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") Integer id, @RequestBody Product product){
       return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Integer id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
