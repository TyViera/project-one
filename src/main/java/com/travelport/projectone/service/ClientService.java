package com.travelport.projectone.service;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import com.travelport.projectone.entities.Purchase;

import java.util.List;
import java.util.Optional;


public interface ClientService {

    List<Client> getClients();

    Optional<Client> findByNif(String nif);

    void deleteByNif(String nif);

    Client save(Client client);

    Optional<Client> update(String nif, Client client);

    List<Purchase> seePastSales(String clientNif);
}
