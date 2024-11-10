package com.travelport.projectone.controller;

import com.travelport.projectone.entities.Purchase;
import com.travelport.projectone.dto.ProductDTO;
import com.travelport.projectone.dto.PurchaseResponseDTO;
import com.travelport.projectone.jpa.PurchaseRepository;
import com.travelport.projectone.jpa.PurchaseDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseDetailsRepository purchaseDetailsRepository;

    @GetMapping("/client")
    @Transactional
    public List<PurchaseResponseDTO> getPastSales(@RequestParam("nif") String nif) {

        List<Purchase> purchases = purchaseRepository.findByClientNif(nif);

        return purchases.stream().map(purchase -> {
            PurchaseResponseDTO purchaseDTO = new PurchaseResponseDTO();
            purchaseDTO.setPurchaseId(purchase.getPurchaseId());
            purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());

            List<ProductDTO> productDTOs = purchase.getPurchaseDetails().stream().map(detail -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductName(detail.getProduct().getName());
                productDTO.setQuantity(detail.getQuantity());
                productDTO.setPrice(detail.getPrice());
                return productDTO;
            }).collect(Collectors.toList());

            purchaseDTO.setProducts(productDTOs);
            return purchaseDTO;
        }).collect(Collectors.toList());
    }
}
