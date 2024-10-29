package com.travelport.jpa;

import com.travelport.entities.Sale;

public interface SalesDao {

    void save(Sale sale);

    void update(Sale sale);

}
