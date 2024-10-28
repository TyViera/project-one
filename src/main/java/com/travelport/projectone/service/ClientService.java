package com.travelport.projectone.service;

import com.travelport.projectone.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> getClientById(Integer id);

    Client save(Client client);

    Client update(Integer id, Client client);

    void deleteById(Integer id);
}
