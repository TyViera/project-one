package com.travelport.projectone.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sales_products")
public class SaleProduct {

  @Id
  @Column(name = "sale_id", nullable = false)
  private int saleId;

  @Id
  @Column(name = "product_id", nullable = false)
  private Integer productId;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "sale_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Sale sale;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
  private Product product;

  public Integer getSaleId() {
    return saleId;
  }

  public void setSaleId(Integer saleId) {
    this.saleId = saleId;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Sale getSale() {
    return sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
    if (sale != null) {
      this.saleId = sale.getId();
    }
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
    if (product != null) {
      this.productId = product.getId();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SaleProduct that)) return false;
    return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(saleId, productId);
  }
}
