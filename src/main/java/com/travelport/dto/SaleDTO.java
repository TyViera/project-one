package com.travelport.dto;

import java.util.List;

public class SaleDTO {

    private String nif;
    private List<ClientSalesInputDTO> products;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public List<ClientSalesInputDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ClientSalesInputDTO> products) {
        this.products = products;
    }
}
