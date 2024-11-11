package com.travelport.projectone.service;

import com.travelport.projectone.entities.Client;
import java.util.List;
import java.util.Optional;


public interface ClientService {

    List<Client>findAll();

    Optional<Client> findById(Integer id);

    void delete(Integer id);

    Optional<Client> save(Client client);

    Optional<Client> update(Integer id, Client client);
}

