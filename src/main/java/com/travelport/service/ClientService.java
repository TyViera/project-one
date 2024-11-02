package com.travelport.service;

import com.travelport.entities.Client;
import com.travelport.persistence.ClientDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void saveClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientDao.save(client);
    }

    public List<Client> findAll() {
        return clientDao.list();
    }

    public Optional<Client> findClientByNif(String nif) {
        return clientDao.findById(nif);
    }

    public void updateClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientDao.update(client);
    }

    public void deleteClientByNif(String nif) {
        clientDao.delete(nif);
    }
}
