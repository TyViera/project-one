package com.travelport.projectone.service;


import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(Product product) {
        productDao.save(product);
        return product;
    }

    @Override
    public Product update(Integer id, Product product) {
        var fproduct = productDao.getProductById(id);
        if (fproduct.isEmpty()) {
            return null;
        }
        if (product.getName() == null) {
            return null;
        }
        productDao.update(product);
        return product;
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public void deleteById(Integer id) {

        productDao.deleteById(id);
    }
}
