package com.travelport.controller;

import com.travelport.entities.*;
import com.travelport.jpa.ClientDao;
import com.travelport.jpa.ProductDao;
import com.travelport.jpa.SalesDao;
import com.travelport.models.SaleDTO;
import com.travelport.models.SaleDetailDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private final SalesDao salesDao;
    private final ClientDao clientDao;
    private final ProductDao productDao;

    public SalesController(SalesDao salesDao, ClientDao clientDao, ProductDao productDao) {
        this.salesDao = salesDao;
        this.clientDao = clientDao;
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<Sale> postSale(@RequestBody SaleDTO saleDTO) {
        // Creamos una Sale (id, client, sellDate, List<SaleDetail>(SaleDetailId, sale, product, quantity))
        var sale = new Sale();

        // Obtenemos el cliente
        Optional<Client> clientOpt = clientDao.findById(saleDTO.getNif());
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        sale.setClient(clientOpt.get());

        // Establecemos la fecha de venta
        sale.setSellDate(new Timestamp(System.currentTimeMillis()));

        // Guardamos la venta
        salesDao.save(sale); // el save lleva el flush, forzamos el Entity Manager a persistir los datos y que se genere el sale_id

        // Creamos los sale details para la Sale
        List<SaleDetail> saleDetails = new ArrayList<>();
        for (SaleDetailDTO detailDTO : saleDTO.getProducts()) {
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
            Optional<Product> productOpt = productDao.findById((detailDTO.getProductCode()));
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
        salesDao.update(sale);

        return ResponseEntity.ok(sale);
    }
}
