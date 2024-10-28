package com.travelport.projectone.model;

import jakarta.validation.constraints.NotEmpty;

public class LoginModel {

    @NotEmpty private String name;
    @NotEmpty private String nif;
    @NotEmpty private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}