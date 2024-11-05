package com.travelport.projectone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.jpa.ClientJpaRepository;

@Service
public class ClientServiceImpl implements ClientService {

  private final ClientJpaRepository clientRepository;

  public ClientServiceImpl(ClientJpaRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Client createClient(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @Override
  public Client updateClients(String clientId, Client updatedClient) {
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    if (clientOptional.isEmpty()) {
      throw new RuntimeException("Client not found with id: " + clientId);
    }
    Client client = clientOptional.get();

    client.mergeWith(updatedClient);

    client.setName(updatedClient.getName());
    client.setAddress(updatedClient.getAddress());

    return clientRepository.save(client);
  }

  @Override
  public void deleteClients(String clientId) {
    clientRepository.deleteById(clientId);
  }

  @Override
  public Client getClientById(String clientId) {
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    if (clientOptional.isEmpty()) {
      throw new RuntimeException("Client not found with id: " + clientId);
    }
    return clientOptional.get();
  }
}
