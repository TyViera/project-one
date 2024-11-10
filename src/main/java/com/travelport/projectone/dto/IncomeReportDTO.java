package com.travelport.projectone.dto;

public class IncomeReportDTO {

    private String productName;
    private long totalUnitsSold;

    public IncomeReportDTO(String productName, long totalUnitsSold) {
        this.productName = productName;
        this.totalUnitsSold = totalUnitsSold;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getTotalUnitsSold() {
        return totalUnitsSold;
    }

    public void setTotalUnitsSold(int totalUnitsSold) {
        this.totalUnitsSold = totalUnitsSold;
    }
}
