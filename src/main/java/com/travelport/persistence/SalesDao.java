package com.travelport.persistence;

import com.travelport.entities.Sale;

import java.util.List;

public interface SalesDao {

    void save(Sale sale);

    void update(Sale sale);

    List<Sale> findByClientNif(String nif);

}
