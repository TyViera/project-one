package com.travelport.projectone.controller;

import com.travelport.projectone.dto.ClientIpDto;
import com.travelport.projectone.dto.SalesPrDto;
import com.travelport.projectone.service.SalesService;
import org.springframework.web.bind.annotation.*;
import com.travelport.projectone.dto.SalesIpDto;

import java.util.Optional;

@RestController
@RequestMapping("/sales")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PATCH, RequestMethod.DELETE})
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {this.salesService = salesService; }

    @PostMapping
    public Optional<SalesPrDto> post(@RequestBody SalesIpDto saleCl) {
        return salesService.save(saleCl);
    }
    @GetMapping("/{id}")
    public Optional<ClientIpDto> getByClientId(Integer id, String DNI) {
        return salesService.findByClientId(id, DNI);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<Sales> update(@PathVariable("code_sales") Integer id, @RequestBody Sales sales) {
//        return ResponseEntity.ok(salesService.update(id, sales));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable("code_sales") Integer id) {
//        salesService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
