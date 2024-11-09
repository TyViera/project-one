package com.travelport.projectone.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    private String nif;

    private String name;
    private String address;

    public String getNif() { return nif; }

    public void setNif(String nif) { this.nif = nif; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

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
