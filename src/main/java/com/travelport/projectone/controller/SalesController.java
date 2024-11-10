package com.travelport.projectone.controller;

import com.travelport.projectone.dto.SaleRequestDTO;
import com.travelport.projectone.dto.ProductSalesDTO;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.entities.Product;
import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.entities.PurchaseDetails;
import com.travelport.projectone.jpa.ClientRepository;
import com.travelport.projectone.jpa.ProductRepository;
import com.travelport.projectone.jpa.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/sell")
    @Transactional
    public ResponseEntity<String> sellProduct(@RequestBody SaleRequestDTO saleRequest) {

        Client client = clientRepository.findByNif(saleRequest.getClientNif());
        if (client == null) {
            return ResponseEntity.badRequest().body("Cliente no encontrado.");
        }

        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setPurchaseDate(new Date());

        List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();

        for (ProductSalesDTO productSale : saleRequest.getProducts()) {

            if (productSale.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("La cantidad debe ser un entero positivo.");
            }

            Product product = productRepository.findById(productSale.getProductId()).orElse(null);

            if (product == null) {
                return ResponseEntity.badRequest().body("Producto con ID " + productSale.getProductId() + " no encontrado.");
            }

            PurchaseDetails purchaseDetail = new PurchaseDetails();
            purchaseDetail.setPurchaseId(purchase);
            purchaseDetail.setProduct(product);
            purchaseDetail.setQuantity(productSale.getQuantity());
            purchaseDetailsList.add(purchaseDetail);
        }

        purchase.setPurchaseDetails(purchaseDetailsList);
        purchaseRepository.save(purchase);

        return ResponseEntity.ok("Venta realizada con éxito.");
    }
}
