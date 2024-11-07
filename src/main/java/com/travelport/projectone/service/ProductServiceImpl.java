package com.travelport.projectone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.travelport.projectone.entities.Product;
import com.travelport.projectone.jpa.ProductJpaRepository;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductJpaRepository productRepository;

  public ProductServiceImpl(ProductJpaRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public Product updateProduct(int productId, Product updatedProduct) {
    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isEmpty()) {
      throw new RuntimeException("Product not found with id: " + productId);
    }
    Product product = productOptional.get();

    product.mergeWith(updatedProduct);

    return productRepository.save(product);
  }

  @Override
  public void deleteProduct(int productId) {
    productRepository.deleteById(productId);
  }

  @Override
  public Product getProductById(int productId) {
    Optional<Product> productOptional = productRepository.findById(productId);
    if (productOptional.isEmpty()) {
      throw new RuntimeException("Product not found with id: " + productId);
    }
    return productOptional.get();
  }
}
