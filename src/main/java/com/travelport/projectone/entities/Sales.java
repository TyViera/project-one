package com.travelport.projectone.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

public class Sales {

    @EmbeddedId
    private SalesPk salesId;

    private Integer quantity;

    public SalesPk getSalesId() {
        return salesId;
    }

    public void setSalesId(SalesPk salesId) {
        this.salesId = salesId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
