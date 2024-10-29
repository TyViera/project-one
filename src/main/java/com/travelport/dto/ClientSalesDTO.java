package com.travelport.dto;

import java.util.List;

public class ClientSalesDTO {

    private String nif;
    private List<SaleResponseDTO> sales;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public List<SaleResponseDTO> getSales() {
        return sales;
    }

    public void setSales(List<SaleResponseDTO> sales) {
        this.sales = sales;
    }
}
