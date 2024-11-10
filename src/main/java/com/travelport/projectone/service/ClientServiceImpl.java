package com.travelport.projectone.service;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.persistence.ClientDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Client save(Client client) {
        clientDao.save(client);
        return client;
    }

    @Override
    public Client update(Integer id, Client client) {
        var fclient = clientDao.getClientById(id);
        if (fclient.isEmpty()) {
            return null;
        }
        if (client.getName() == null) {
            return null;
        }
        clientDao.update(client);
        return client;
    }

    @Override
    public Optional<Client> getClientById(Integer id) {
        return clientDao.getClientById(id);
    }

    @Override
    public void deleteById(Integer id) {

        clientDao.deleteById(id);
    }
}
