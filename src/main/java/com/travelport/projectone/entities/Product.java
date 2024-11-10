package com.travelport.projectone.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private int code;

    @Column(nullable = false)
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return code == product.code && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}