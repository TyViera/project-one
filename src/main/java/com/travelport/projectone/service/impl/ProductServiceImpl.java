package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Product;
import com.travelport.projectone.jpa.ProductJpaRepository;
import java.util.List;
import java.util.Optional;
import com.travelport.projectone.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productDao;

    public ProductServiceImpl(ProductJpaRepository productDao) { this.productDao = productDao; }

    @Override
    public List<Product> findAll() { return productDao.findAll(); }

    @Override
    public Optional<Product> findById(Integer id){

        var coincidentProduct = productDao.findById(id);
        if (coincidentProduct.isEmpty()) {
            return Optional.empty();
        }
        return coincidentProduct;
    }

    @Override
    public void delete(Integer id) {

        var coincidentProduct = productDao.existsById(id);
        if (coincidentProduct){
            productDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client doesn't exists");
        }
    }

    @Override
    public Optional<Product> save(Product product) {
        if (productDao.existsById(product.getId())) {
            return null;
        }
        productDao.save(product);
        return Optional.of(productDao.save(product));
    }

    @Override
    public Optional<Product> update(Integer id, Product product) {
        var fProduct = productDao.findById(id);
        if (fProduct.isEmpty()) {
            return null;
        }
        var productUpdate = fProduct.get();
        if (product.getName() == null ) {
            return null;
        } else {
            productUpdate.setName(product.getName());
        }

        var productUpdated = productDao.save(productUpdate);
        return Optional.of(productUpdated);
    }
}
