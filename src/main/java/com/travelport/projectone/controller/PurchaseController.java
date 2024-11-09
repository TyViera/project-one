package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) { this.purchaseService = purchaseService; }

    @GetMapping
    public List<Purchase> getPurchase() {
        var purchases = purchaseService.getPurchase();
        System.out.println(purchases);
        return purchases;
    }

    @PostMapping("/buy")
    public Purchase postPurchase(@RequestBody Purchase purchase) { return purchaseService.save(purchase); }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("id") Integer id) {
        var purchase = purchaseService.findById(id);
        return purchase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable("id") Integer id, @RequestBody Purchase purchase) {
        return ResponseEntity.ok(purchaseService.update(id, purchase));
    }

    @DeleteMapping
    public ResponseEntity<Purchase> deletePurchase(@RequestParam("id") Integer id) {
        purchaseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
