package com.travelport.projectone.service.impl;

import com.travelport.projectone.entity.Client;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientJpaRepository clientDao;

    public ClientServiceImpl(ClientJpaRepository clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public Optional<Client> save(Client client) {
        if (client == null) return Optional.empty();
        if (clientDao.existsById(client.getNif())) return Optional.empty();

        try {
            return Optional.of(clientDao.save(client));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> update(String id, Client client) {
        var foundClient = clientDao.findById(id);
        if (foundClient.isEmpty()) return Optional.empty();

        var clientToUpdate = foundClient.get();
        if (client.getNif() != null) clientToUpdate.setNif(client.getNif());
        if (client.getName() != null) clientToUpdate.setName(client.getName());
        if (client.getAddress() != null) clientToUpdate.setAddress(client.getAddress());

        var savedClient = clientDao.save(clientToUpdate);
        return Optional.of(savedClient);
    }

    @Override
    public Boolean delete(String id) {
        var isClientExist = clientDao.existsById(id);
        if (!isClientExist) return false;

        clientDao.deleteById(id);
        return true;
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public Optional<Client> findById(String id) {
        var foundClient = clientDao.findById(id);
        if (foundClient.isEmpty()) return Optional.empty();

        return foundClient;
    }
}
