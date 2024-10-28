package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public Client postClient(@RequestBody Client client) {
        clientService.save(client);
        return client;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Integer id) {
        var client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateById(@PathVariable("id") Integer id, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(id, client));
    }


}
