package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "purchaseDetails")
public class PurchaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailID")
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "purchaseID", referencedColumnName = "purchaseID", nullable = false)
    private Purchase purchaseId;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "code", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public Purchase getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Purchase purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseDetails that)) return false;
        return detailId == that.detailId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(detailId);
    }
}