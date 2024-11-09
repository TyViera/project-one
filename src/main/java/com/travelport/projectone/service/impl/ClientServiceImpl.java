package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.persistence.ClientDao;
import com.travelport.projectone.service.ClientService;
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
    public List<Client> getClients() {
        return clientDao.list();
    }

    @Override
    public Optional<Client> findByNif(String nif) {
        return clientDao.getClientByNif(nif);
    }

    @Override
    public void deleteByNif(String nif) {
        clientDao.deleteByNif(nif);
    }

    @Override
    public Client save(Client client) {
        clientDao.save(client);
        return client;
    }

    @Override
    public Optional<Client> update(String nif, Client client) {
        return clientDao.update(nif, client);
    }

    @Override
    public List<Purchase> seePastSales(String clientNif) {
        return clientDao.seePastSales(clientNif);
    }
}
