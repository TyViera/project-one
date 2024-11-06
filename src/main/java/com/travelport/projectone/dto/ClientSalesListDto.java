package com.travelport.projectone.dto;

import java.util.List;

public class ClientSalesListDto {

    private String clientNif;
    private List<SaleDto> sales;

    public ClientSalesListDto(String clientNif, List<SaleDto> sales) {
        this.clientNif = clientNif;
        this.sales = sales;
    }

    public String getClientNif() {
        return clientNif;
    }

    public void setClientNif(String clientNif) {
        this.clientNif = clientNif;
    }

    public List<SaleDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleDto> sales) {
        this.sales = sales;
    }

}
