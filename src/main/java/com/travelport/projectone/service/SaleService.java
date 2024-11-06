package com.travelport.projectone.service;

import com.travelport.projectone.dto.ClientSalesListDto;
import com.travelport.projectone.dto.PurchaseDto;
import com.travelport.projectone.dto.SaleDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public interface SaleService {

    Optional<SaleDto> save(@RequestBody PurchaseDto req);

    Optional<ClientSalesListDto> findSalesByClientId(@PathVariable("id") String clientNif);

}
