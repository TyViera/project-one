package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.jpa.ClientJpaRepository;
import java.util.List;
import java.util.Optional;

import com.travelport.projectone.service.ClientService;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientJpaRepository clientDao;

    public ClientServiceImpl(ClientJpaRepository clientDao) { this.clientDao = clientDao; }

    @Override
    public List<Client> findAll() { return clientDao.findAll(); }

    @Override
    public Optional<Client> findById(Integer id) {
        var coincidentClient = clientDao.findById(id);
        if (coincidentClient.isEmpty()) {
            return Optional.empty();
        }
        return coincidentClient;
    }

    @Override
    public void delete(Integer id) {
        var coincidentClient = clientDao.existsById(id);
        if (coincidentClient){
            clientDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client doesn't exists");
        }
    }

    @Override
    public Optional<Client> save(Client client) {
        if (clientDao.existsById(client.getId())) {
            return null;
        }
        clientDao.save(client);
        return Optional.of(clientDao.save(client));
    }

    @Override
    public Optional<Client> update(Integer id, Client client) {
        var fClient = clientDao.findById(id);
        if (fClient.isEmpty()) {
            return null;
        }
        var clientUpdate = fClient.get();
        if (client.getName() == null || client.getDNI() == null || client.getName() == null) {
            return null;
        } else {
            clientUpdate.setName(client.getName());
            clientUpdate.setDNI(client.getDNI());
            clientUpdate.setAddress(client.getAddress());
        }

        var clientUpdated = clientDao.save(clientUpdate);
        return Optional.of(clientUpdated);
    }
}
