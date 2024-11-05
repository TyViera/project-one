package com.travelport.projectone.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sales_products")
@IdClass(SaleProduct.SaleProductId.class)
public class SaleProduct {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public SaleProduct() {}

    public SaleProduct(Sale sale, Product product, Integer amount) {
        this.sale = sale;
        this.product = product;
        this.amount = amount;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public static class SaleProductId implements Serializable {
        private Integer sale;
        private Integer product;

        public SaleProductId() {}

        public SaleProductId(Integer sale, Integer product) {
            this.sale = sale;
            this.product = product;
        }

        public Integer getSale() {
            return sale;
        }

        public void setSale(Integer sale) {
            this.sale = sale;
        }

        public Integer getProduct() {
            return product;
        }

        public void setProduct(Integer product) {
            this.product = product;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SaleProductId that)) return false;
            return Objects.equals(sale, that.sale) && Objects.equals(product, that.product);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sale, product);
        }
    }

}
