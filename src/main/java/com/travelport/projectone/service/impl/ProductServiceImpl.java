package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.persistence.ProductDao;
import com.travelport.projectone.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao){
        this.productDao=productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    @Transactional
    public Optional<Integer> deleteById(Integer id) {
        return productDao.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product update(Integer id, Product product) {
        var optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return null;
        }

        Product existingProduct = optionalProduct.get();

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }
        if (product.getCode() != null) {
            existingProduct.setCode(product.getCode());
        }

        productDao.update(existingProduct);
        return existingProduct;
    }

}
