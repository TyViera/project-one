package com.travelport.projectone.service;

import java.util.List;

import com.travelport.projectone.entities.Client;

public interface ClientService {
  Client createClient(Client client);
  List<Client> getAllClients();
  Client updateClients(String id, Client updatedClient);
  void deleteClients(String id);
  Client getClientById(String id);
}
