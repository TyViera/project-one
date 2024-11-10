package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.persistence.ClientDao;
import com.travelport.projectone.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    public ClientServiceImpl(ClientDao clientDao){
        this.clientDao=clientDao;
    }
    @Override
    public List<Client> findAll(){
        return clientDao.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id){
        return clientDao.findById(id);
    }

    @Override
    public void deleteById(Integer id){
        clientDao.deleteById(id);
    }

    @Override
    public Client save(Client client){
        return clientDao.save(client);
    }

    @Override
    public Client update(Integer id, Client client) {
        var optionalClient = clientDao.findById(id);

        if (optionalClient.isEmpty()) {
            return null;
        }

        Client existingClient = optionalClient.get();

        if (client.getName() != null) {
            existingClient.setName(client.getName());
        }
        if (client.getAddress() != null) {
            existingClient.setAddress(client.getAddress());
        }
        clientDao.update(existingClient);
        return existingClient;
    }
}
