package com.travelport.projectone.service;

import com.travelport.projectone.entities.Client;
import java.util.List;
import java.util.Optional;


public interface ClientService {

    List<Client> getClients();

    Optional<Client> findByNif(String nif);

    void deleteByNif(String nif);

    Client save(Client client);

    Client update(String nif, Client client);
}
