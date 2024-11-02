package com.travelport.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SaleDetailId implements Serializable {

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "product_code")
    private Integer productCode;

    public SaleDetailId(Integer saleId, Integer productCode) {
        this.saleId = saleId;
        this.productCode = productCode;
    }

    public SaleDetailId() {}

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleDetailId that = (SaleDetailId) o;
        return Objects.equals(saleId, that.saleId) &&
                Objects.equals(productCode, that.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, productCode);
    }
}
