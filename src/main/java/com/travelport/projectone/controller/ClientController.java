package com.travelport.projectone.controller;

import com.travelport.projectone.entity.Client;
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

    @PostMapping
    public ResponseEntity<Client> post(@RequestBody Client client) {
        var savedUser = clientService.save(client);
        if (savedUser.isEmpty()) return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.ok(savedUser.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") String id, @RequestBody Client client) {
        var updatedClient = clientService.update(id, client);
        if (updatedClient.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedClient.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable("id") String id) {
        var isClientDeleted = clientService.delete(id);
        if (!isClientDeleted) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Client> get() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") String id) {
        var foundClient = clientService.findById(id);
        if (foundClient.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(foundClient.get());
    }
}
