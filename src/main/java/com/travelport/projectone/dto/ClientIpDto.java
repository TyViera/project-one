package com.travelport.projectone.dto;

import java.util.List;

public class ClientIpDto {
    private String DNI;
    private List<SalesPrDto> sales;

    public ClientIpDto(String DNI, List<SalesPrDto> sales) {
        this.DNI = DNI;
        this.sales = sales;
    }

    public String getDNI() { return DNI; }
    public void setDNI(String DNI) { this.DNI = DNI; }

    public List<SalesPrDto> getSales() { return sales; }
    public void setSales(List<SalesPrDto> sales) { this.sales = sales; }
}
