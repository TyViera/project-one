package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_client", nullable = false)
    private Client client;

    @Column(name = "sale_date", nullable = false)
    private Timestamp sellDate;

    public Integer getCode_sale() { return id; }
    public void setCode_sale(Integer code_sale) { this.id = code_sale; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Timestamp getSellDate() { return sellDate; }
    public void setSellDate(Timestamp sellDate) { this.sellDate = sellDate; }

    public  Sales() {} //si no peta @Entity

    public Sales(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sales sales)) return false;
        return Objects.equals(id, sales.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
