package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) { this.clientService = clientService; }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PostMapping
    public Client postClient(@RequestBody Client client) { return clientService.save(client); }

    @GetMapping("/{nif}")
    public ResponseEntity<Client> getClientByNif(@PathVariable("nif") String nif) {
        var client = clientService.findByNif(nif);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{nif}")
    public ResponseEntity<Client> updateClient(@PathVariable("nif") String nif, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(nif, client));
    }

    @DeleteMapping("/{nif}")
    public ResponseEntity<Client> deleteClient(@PathVariable("nif") String nif) {
        clientService.deleteByNif(nif);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sales/{nif}")
    public ResponseEntity<List<Purchase>> seePastSales(@PathVariable("nif") String nif) {
        List<Purchase> purchases = clientService.seePastSales(nif);
        if (purchases == null || purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }
}
