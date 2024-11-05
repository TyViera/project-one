package com.travelport.projectone.controller;


import com.travelport.projectone.dto.ClientSalesListDto;
import com.travelport.projectone.dto.ProductDto;
import com.travelport.projectone.dto.PurchaseDto;
import com.travelport.projectone.dto.SaleDto;
import com.travelport.projectone.entity.Sale;
import com.travelport.projectone.entity.SaleProduct;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.jpa.ProductJpaRepository;
import com.travelport.projectone.jpa.SaleJpaRepository;
import com.travelport.projectone.jpa.SaleProductJpaRepository;
import com.travelport.projectone.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    ResponseEntity<SaleDto> postSale(@RequestBody PurchaseDto req) {
        var createdSale = saleService.save(req);
        if (createdSale.isEmpty()) return ResponseEntity.unprocessableEntity().build();

        return ResponseEntity.ok(createdSale.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientSalesListDto> getSalesByClientId(@PathVariable("id") String clientNif) {
        var foundSales = saleService.findSalesByClientId(clientNif);
        if (foundSales.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(foundSales.get());
    }

}
