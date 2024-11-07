package com.travelport.persistence;


import com.travelport.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    Client save(Client client);

    List<Client> findAll();

    Optional<Client> findById(Integer id);

    void update(Client client);

    Optional<Integer> deleteById(Integer id);

}
