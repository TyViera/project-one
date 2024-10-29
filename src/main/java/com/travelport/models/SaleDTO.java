package com.travelport.models;

import java.util.List;

public class SaleDTO {

    private String nif;
    private List<SaleDetailDTO> products;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public List<SaleDetailDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SaleDetailDTO> products) {
        this.products = products;
    }
}
