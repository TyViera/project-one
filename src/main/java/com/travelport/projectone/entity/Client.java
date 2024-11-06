package com.travelport.projectone.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "nif", length = 9, nullable = false)
    private String nif;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "address", length = 40, nullable = false)
    private String address;

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(nif, client.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nif);
    }
}
