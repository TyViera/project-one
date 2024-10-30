package com.travelport.dto;

import java.util.List;

public class ClientSalesOutputDTO {

    private String nif;
    private List<SaleOutputDTO> sales;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public List<SaleOutputDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleOutputDTO> sales) {
        this.sales = sales;
    }
}
