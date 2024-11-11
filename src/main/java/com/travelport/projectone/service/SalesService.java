package com.travelport.projectone.service;

import com.travelport.projectone.dto.ClientIpDto;
import com.travelport.projectone.dto.SalesIpDto;
import com.travelport.projectone.dto.SalesPrDto;

import java.util.Optional;

public interface SalesService {

    Optional<ClientIpDto> findByClientId(Integer id, String DNI);

    Optional<SalesPrDto> save(SalesIpDto saleCl);
}
