package com.travelport.projectone.service;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImp(ProductDao productDao) { this.productDao = productDao; }

    @Override
    public List<Product> getProduct() { return productDao.list(); }

    @Override
    public Optional<Product> findByCode(Integer code) {
        return productDao.getProductById(code);
    }

    @Override
    public void deleteByCode(Integer code) {
        productDao.deleteById(code);
    }

    @Override
    public Product save(Product product) {
        productDao.save(product);
        return product;
    }

    @Override
    public Product update(Integer code, Product product) {
        var fproduct = productDao.getProductById(code);
        if (fproduct.isEmpty()) {
            return null;
        }
        if (product.getCode() == null){
            return null;
        }
        productDao.update(product);
        return product;
    }

}
