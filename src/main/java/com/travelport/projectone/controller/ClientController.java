package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.persistence.ClientDao;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientDao.list();
    }

    @PostMapping
    public Client postClient(@RequestBody Client client) {
        clientDao.save(client);
        return client;
    }

    @GetMapping("/{nif}")
    public ResponseEntity<Client> getClientByNif(@PathVariable("nif") String nif) {
        var client = clientDao.getClientByNif(nif);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{nif}")
    public ResponseEntity<Client> getClientByNif(@PathVariable("nif") String nif, @RequestBody Client client) {
        var findClient = clientDao.getClientByNif(nif);
        if (findClient.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        if (client.getName() != null && !client.getName().isEmpty()) {
            findClient.get().setName(client.getName());
        }
        if (client.getAddress() != null && !client.getAddress().isEmpty()) {
            findClient.get().setAddress(client.getAddress());
        }

        clientDao.update(findClient.get());
        return ResponseEntity.ok(findClient.get());
    }

    @DeleteMapping("/{nif}")
    public ResponseEntity<Void> deleteClientById(@PathVariable("nif") String nif) {
        var client = clientDao.deleteByNif(nif);
        return client.map(u -> ResponseEntity.noContent().<Void>build()).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
