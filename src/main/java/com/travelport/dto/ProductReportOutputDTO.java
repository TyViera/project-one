package com.travelport.dto;

public class ProductReportOutputDTO {

    private Integer productCode;
    private String productName;
    private int totalQuantity;

    public ProductReportOutputDTO(Integer productCode, String productName, Number totalQuantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.totalQuantity = totalQuantity.intValue();
    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
