package com.travelport.projectone.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(SaleDetails.Sale_detail_ID.class)
@Table(name = "sales_details")
public class SaleDetails {

    @Id
    @Column(name = "code_sale", nullable = false)
    private Integer code_sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_sale", referencedColumnName = "id", insertable = false, updatable = false)
    private Sales sales;

    @Id
    @Column(name = "code_product", nullable = false)
    private Product product;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "code_product", referencedColumnName = "id", insertable = false, updatable = false)
//    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Integer getSaleId() { return code_sale; }
    public void setSaleId(Integer saleId) { this.code_sale = saleId; }

//    public Integer getProductId() { return code_product; }
//    public void setProductId(Integer productId) { this.code_product = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Sales getSale() { return sales; }
    public void setSale(Sales sales) { this.sales = sales; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public SaleDetails(){} //Si no peta @Entity

    public SaleDetails(Sales sales, Product product, Integer quantity) {
        this.sales = sales;
        this.product = product;
        this.quantity = quantity;
    }

    public static class Sale_detail_ID implements Serializable {
        private Integer code_sale;
        private Integer code_product;

        public Sale_detail_ID() {}
        public Sale_detail_ID(Integer code_sale, Integer code_product) {
            this.code_sale = code_sale;
            this.code_product = code_product;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Sale_detail_ID that)) return false;
            return Objects.equals(code_sale, that.code_sale) && Objects.equals(code_product, that.code_product);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code_sale, code_product);
        }
    }
}
