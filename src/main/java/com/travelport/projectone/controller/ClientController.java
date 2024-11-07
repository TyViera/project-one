package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  @PostMapping
  public Client createClient(@RequestBody Client client) {
    return clientService.createClient(client);
  }

  @GetMapping("/{clientId}")
  public ResponseEntity<Client> getClientById(@PathVariable String clientId) {
    return ResponseEntity.ok(clientService.getClientById(clientId));
  }

  @PatchMapping("/{clientId}")
  public ResponseEntity<Client> updateClient(@PathVariable String clientId, @RequestBody Client client) {
    return ResponseEntity.ok(clientService.updateClients(clientId, client));
  }

  @DeleteMapping("/{clientId}")
  public ResponseEntity<Void> deleteClient(@PathVariable String clientId) {
    clientService.deleteClients(clientId);
    return ResponseEntity.noContent().build();
  }
}
