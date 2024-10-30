package com.travelport.controller;

import com.travelport.entities.Client;
import com.travelport.service.ClientService;
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
    public List<Client> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/{nif}")
    public ResponseEntity<Client> getClientByNif(@PathVariable("nif") String nif) {
        var client = clientService.findClientByNif(nif);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Client postClient(@RequestBody Client client) {
        clientService.SaveClient(client);
        return client;
    }

    @PatchMapping("/{nif}")
    public ResponseEntity<Client> updateClient(@PathVariable("nif") String nif, @RequestBody Client client) {
        var findClient = clientService.findClientByNif(nif);
        if (findClient.isPresent()) {
            findClient.get().setName(client.getName());
            findClient.get().setAddress(client.getAddress());
            clientService.updateClient(findClient.get());
            return ResponseEntity.ok(findClient.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nif}")
    public ResponseEntity<Void> deleteClient(@PathVariable("nif") String nif) {
        clientService.deleteClientByNif(nif);
        return ResponseEntity.noContent().build();
    }


}