package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(SaleDetail.SaleDetailPK.class)
@Table(name = "sales_det")
public class SaleDetail {

    @Id
    @Column(name = "sale_id", nullable = false)
    private Integer saleId;

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

    public static class SaleDetailPK implements Serializable {
        protected Integer saleId;
        protected Integer productId;

        public SaleDetailPK() {}

        public SaleDetailPK(Integer saleId, Integer productId) {
            this.saleId = saleId;
            this.productId = productId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SaleDetailPK that)) return false;
            return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(saleId, productId);
        }
    }

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
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleDetail that)) return false;
        return Objects.equals(saleId, that.saleId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productId);
    }
}
