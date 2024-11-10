package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Sales;

import java.util.List;
import java.util.Optional;

public interface SalesDao {
    void save(Sales sales);

    List<Sales> list();

    Optional<Sales> getSalesById(Integer id);

    void update(Sales sales);

    Optional<Integer> deleteById(Integer id);

}
