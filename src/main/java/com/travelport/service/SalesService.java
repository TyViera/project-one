package com.travelport.service;

import com.travelport.dto.ClientSalesDTO;
import com.travelport.dto.SaleDetailResponseDTO;
import com.travelport.dto.SaleResponseDTO;
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

    public ClientSalesDTO getClientSalesDTO(String nif) {
        List<Sale> sales = getSalesByClientNif(nif);

        ClientSalesDTO clientSalesDTO = new ClientSalesDTO();
        clientSalesDTO.setNif(nif);

        List<SaleResponseDTO> saleResponseDTOs = sales.stream().map(sale -> {
            SaleResponseDTO saleDTO = new SaleResponseDTO();
            saleDTO.setSaleId(sale.getId());

            List<SaleDetailResponseDTO> productDTOs = sale.getSaleDetails().stream().map(saleDetail -> {
                SaleDetailResponseDTO detailDTO = new SaleDetailResponseDTO();
                detailDTO.setCode(saleDetail.getProduct().getCode());
                detailDTO.setQuantity(saleDetail.getQuantity());
                return detailDTO;
            }).collect(Collectors.toList());

            saleDTO.setProducts(productDTOs);
            return saleDTO;
        }).collect(Collectors.toList());

        clientSalesDTO.setSales(saleResponseDTOs);

        return clientSalesDTO;
    }

}
