package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import com.travelport.projectone.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) { this.productDao = productDao; }

    @Override
    public List<Product> getProduct() { return productDao.list(); }

    @Override
    public Optional<Product> findByCode(Integer code) {
        return productDao.getProductByCode(code);
    }

    @Override
    public void deleteByCode(Integer code) {
        productDao.deleteByCode(code);
    }

    @Override
    public Product save(Product product) {
        productDao.save(product);
        return product;
    }

    @Override
    public Product update(Integer code, Product product) {
        var fproduct = productDao.getProductByCode(code);
        if (fproduct.isEmpty()) {
            return null;
        }
        if (product.getCode() == null){
            return null;
        }
        productDao.update(product);
        return product;
    }

    @Override
    public List<Product> incomeReport() {
        return productDao.incomeReport();
    }
}
