package com.travelport.projectone.entities;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product product)) return false;
    return id == product.id;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(id);
  }

  public Product mergeWith(Product updatedProduct) {
    if (updatedProduct.getName() != null && !updatedProduct.getName().isEmpty()) {
      this.name = updatedProduct.getName();
    }
    return this;
  }
}
