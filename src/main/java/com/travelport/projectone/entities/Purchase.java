package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseID")
    private int purchaseId;

    @ManyToOne
    @JoinColumn(name = "clientNIF", referencedColumnName = "nif", nullable = false)
    private Client client;

    @Column(nullable = false)
    private Date purchaseDate;

    @OneToMany(mappedBy = "purchaseId", cascade = CascadeType.ALL)
    private List<PurchaseDetails> purchaseDetails;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return purchaseId == purchase.purchaseId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(purchaseId);
    }
}