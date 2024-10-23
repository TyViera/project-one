package com.travelport.projectone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private String name;

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
