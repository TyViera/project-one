package com.travelport.projectone.service;

import com.travelport.projectone.entities.Sales;

import java.util.List;
import java.util.Optional;

public interface SalesService {

    Sales save(Sales sales);

    Optional<Sales> getSalesById(Integer id);

    Sales update(Integer id, Sales sales);

    void deleteById(Integer id);
}
