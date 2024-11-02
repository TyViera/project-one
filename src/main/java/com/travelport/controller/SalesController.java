package com.travelport.controller;

import com.travelport.dto.ClientSalesOutputDTO;
import com.travelport.entities.*;
import com.travelport.dto.SaleDTO;
import com.travelport.dto.ClientSalesInputDTO;
import com.travelport.service.ClientService;
import com.travelport.service.ProductService;
import com.travelport.service.SalesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final ProductService productService;
    private final SalesService salesService;
    private final ClientService clientService;

    public SalesController(ProductService productService, SalesService salesService, ClientService clientService) {
        this.salesService = salesService;
        this.productService = productService;
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Sale> postSale(@RequestBody SaleDTO saleDTO) {
        // Creamos una Sale (id, client, sellDate, List<SaleDetail>(SaleDetailId, sale, product, quantity))
        var sale = new Sale();

        // Obtenemos el cliente
        Optional<Client> clientOpt = clientService.findClientByNif(saleDTO.getNif());
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        sale.setClient(clientOpt.get());

        // Establecemos la fecha de venta
        sale.setSellDate(new Timestamp(System.currentTimeMillis()));

        // Guardamos la venta
        salesService.saveSale(sale); // el save lleva el flush, forzamos el Entity Manager a persistir los datos y que se genere el sale_id

        // Creamos los sale details para la Sale
        List<SaleDetail> saleDetails = new ArrayList<>();
        for (ClientSalesInputDTO detailDTO : saleDTO.getProducts()) {
            // Validaciones previas
            if (detailDTO.getQuantity() == null) {
                return ResponseEntity.notFound().build();
            }
            if (detailDTO.getQuantity() <= 0) {
                return ResponseEntity.notFound().build();
            }
            if (detailDTO.getQuantity() % 1 != 0) {
                return ResponseEntity.notFound().build();
            }

            // Obtenemos el producto
            Optional<Product> productOpt = productService.findProductById((detailDTO.getProductCode()));
            if (productOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Sale Detail
            SaleDetail saleDetail = new SaleDetail(); // (id, sale_id, product_code, quantity)
            // Sale Detail Id
            SaleDetailId saleDetailId = new SaleDetailId();
            saleDetailId.setSaleId(sale.getId());
            saleDetailId.setProductCode(productOpt.get().getCode());

            // Sale Detail
            saleDetail.setId(saleDetailId);
            saleDetail.setSale(sale);
            saleDetail.setProduct(productOpt.get());
            saleDetail.setQuantity(detailDTO.getQuantity().intValue());
            saleDetails.add(saleDetail);
        }

        sale.setSaleDetails(saleDetails);

        // Actualizar para añadir los details
        salesService.updateSale(sale);

        return ResponseEntity.ok(sale);
    }

    @GetMapping("/{nif}")
    public ResponseEntity<ClientSalesOutputDTO> getSalesByClient(@PathVariable("nif") String nif) {
        ClientSalesOutputDTO clientSalesOutputDTO = salesService.getClientSalesDTO(nif);
        if (clientSalesOutputDTO.getSales().isEmpty() ) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientSalesOutputDTO);
    }
}
