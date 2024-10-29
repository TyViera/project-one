package com.travelport.controller;

import com.travelport.entities.Client;
import com.travelport.exceptions.ClientNotFoundException;
import com.travelport.jpa.ClientDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientDao.list();
    }

    @GetMapping("/{nif}")
    public ResponseEntity<Client> getClientByNif(@PathVariable("nif") String nif) {
        var client = clientDao.findById(nif);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Client postClient(@RequestBody Client client) {
        clientDao.save(client);
        return client;
    }

    @PatchMapping("/{nif}")
    public ResponseEntity<Client> updateClient(@PathVariable("nif") String nif, @RequestBody Client client) {
        var findClient = clientDao.findById(nif);
        if (findClient.isPresent()) {
            findClient.get().setName(client.getName());
            findClient.get().setAddress(client.getAddress());
            clientDao.update(findClient.get());
            return ResponseEntity.ok(findClient.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{nif}")
    public ResponseEntity<Void> deleteClient(@PathVariable("nif") String nif) {
        clientDao.delete(nif);
        return ResponseEntity.noContent().build();
    }


}