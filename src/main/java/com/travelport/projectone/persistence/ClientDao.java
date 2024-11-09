package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Purchase;

import java.util.List;
import java.util.Optional;

public interface ClientDao {

    void save(Client client);

    List<Client> list();

    Optional<Client> getClientByNif(String nif);

    Optional<Client> update(String nif, Client client);

    void deleteByNif(String nif);

    List<Purchase> seePastSales(String clientNif);
}
