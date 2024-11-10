package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Client;
import java.util.List;
import java.util.Optional;

public interface ClientDao {
    void save(Client client);

    List<Client> list();

    Optional<Client> getClientByNif(String nif);

    void update(Client client);

    Optional<String> deleteByNif(String nif);
}
