package com.travelport.projectone.service.impl;

import com.travelport.projectone.entity.Product;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productDao;

    public ProductServiceImpl(ProductJpaRepository productDao) {
        this.productDao = productDao;
    }

    @Override
    public Optional<Product> save(Product product) {
        if (product == null) return Optional.empty();

        try {
            return Optional.of(productDao.save(product));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> update(Integer id, Product product) {
        var foundProduct = productDao.findById(id);
        if (foundProduct.isEmpty()) return Optional.empty();

        var productToUpdate = foundProduct.get();
        if (product.getId() != null) productToUpdate.setId(product.getId());
        if (product.getName() != null) productToUpdate.setName(product.getName());
        if (product.getTimesSold() != null) productToUpdate.setTimesSold(product.getTimesSold());

        var savedProduct = productDao.save(productToUpdate);
        return Optional.of(savedProduct);
    }

    @Override
    public Boolean delete(Integer id) {
        var isProductExist = productDao.existsById(id);
        if (!isProductExist) return false;

        productDao.deleteById(id);
        return true;
    }

    @Override
    public List<Product> findAll(String orderBy) {
        if (orderBy == null) return productDao.findAll();

        if (orderBy.equalsIgnoreCase("timessold")) return productDao.findAllByOrderByTimesSoldDesc();
        else return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        var foundProduct = productDao.findById(id);
        if (foundProduct.isEmpty()) return Optional.empty();

        return foundProduct;
    }

}
