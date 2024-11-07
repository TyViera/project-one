package com.travelport.projectone.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {
  @Id
  private String id;

  private String name;
  private String address;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return Objects.equals(id, client.id) && Objects.equals(name,
        client.name) && Objects.equals(address, client.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address);
  }

  public Client mergeWith(Client updatedClient) {
    if (updatedClient.getName() != null && !updatedClient.getName().isEmpty()) {
      this.name = updatedClient.getName();
    }
    if (updatedClient.getAddress() != null && !updatedClient.getAddress().isEmpty()) {
      this.address = updatedClient.getAddress();
    }
    return this;
  }

}
