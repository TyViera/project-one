package com.travelport.service;

import com.travelport.entities.Product;
import com.travelport.persistence.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void saveProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.list();
    }

    public Optional<Product> findProductById(int id) {
        return productDao.findById(id);
    }

    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        productDao.update(product);
    }

    public void deleteProduct(int id) {
        productDao.delete(id);
    }
}
