package com.travelport.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sales_cab")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // le decimos que es autoincrement
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_nif", referencedColumnName = "nif", nullable = false)
    private Client client;

    @Column(name = "sell_date", nullable = false)
    private Timestamp sellDate;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SaleDetail> saleDetails = new ArrayList<>();

    public Sale() {
        this.sellDate = new Timestamp(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getSellDate() {
        return sellDate;
    }

    public void setSellDate(Timestamp sellDate) {
        this.sellDate = sellDate;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale sale)) return false;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
