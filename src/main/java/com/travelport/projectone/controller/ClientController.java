package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<Client>> updateClient(@PathVariable("nif") String nif, @RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(nif, client));
    }

    @DeleteMapping
    public ResponseEntity<Client> deleteClient(@RequestParam("nif") String nif) {
        clientService.deleteByNif(nif);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Purchase>> seePastSales(@RequestParam("nif") String nif) {
        List<Purchase> purchases = clientService.seePastSales(nif);
        if (purchases == null || purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }
}
