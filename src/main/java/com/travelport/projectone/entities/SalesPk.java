package com.travelport.projectone.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

@Embeddable
public class SalesPk {

    @JoinColumn(name = "client_id")
    private Integer clientId;

    @JoinColumn(name = "product_id")
    private Integer productId;
}
