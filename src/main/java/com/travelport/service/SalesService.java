package com.travelport.service;

import com.travelport.dto.ClientSalesOutputDTO;
import com.travelport.dto.SaleDetailOutputDTO;
import com.travelport.dto.SaleOutputDTO;
import com.travelport.entities.Sale;
import com.travelport.jpa.SalesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SalesService {

    private final SalesDao salesDao;

    public SalesService(SalesDao salesDao) {
        this.salesDao = salesDao;
    }

    public List<Sale> getSalesByClientNif(String nif) {
        return salesDao.findByClientNif(nif);
    }

    public ClientSalesOutputDTO getClientSalesDTO(String nif) {
        List<Sale> sales = getSalesByClientNif(nif);

        ClientSalesOutputDTO clientSalesOutputDTO = new ClientSalesOutputDTO();
        clientSalesOutputDTO.setNif(nif);

        List<SaleOutputDTO> saleOutputDTOS = sales.stream().map(sale -> {
            SaleOutputDTO saleOutputDTO = new SaleOutputDTO();
            saleOutputDTO.setSaleId(sale.getId());

            List<SaleDetailOutputDTO> productDTOs = sale.getSaleDetails().stream().map(saleDetail -> {
                SaleDetailOutputDTO detailDTO = new SaleDetailOutputDTO();
                detailDTO.setCode(saleDetail.getProduct().getCode());
                detailDTO.setQuantity(saleDetail.getQuantity());
                return detailDTO;
            }).collect(Collectors.toList());

            saleOutputDTO.setProducts(productDTOs);
            return saleOutputDTO;
        }).collect(Collectors.toList());

        clientSalesOutputDTO.setSales(saleOutputDTOS);

        return clientSalesOutputDTO;
    }

    public void saveSale(Sale sale) {
        salesDao.save(sale);
    }

    public void updateSale(Sale sale) {
        salesDao.update(sale);
    }

}
