package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    private int id;

    private String clientNif;
    private Integer productCode;

    private int quantity;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getClientNif() { return clientNif; }

    public void setClientNif(String clientNif) { this.clientNif = clientNif; }

    public Integer getProductCode() { return productCode; }

    public void setProductCode(Integer productCode) { this.productCode = productCode; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
