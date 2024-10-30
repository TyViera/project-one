package com.travelport.persistence;

import com.travelport.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends AbstractEntityDao<Client, String> {

    void save(Client client);

    List<Client> list();

    Optional<Client> findById(String nif);

    void update(Client client);

    void delete(String nif);

}
