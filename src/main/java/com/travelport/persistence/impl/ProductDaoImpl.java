package com.travelport.persistence.impl;

import com.travelport.entities.Product;
import com.travelport.persistence.ProductDao;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl extends AbstractEntityDaoImpl<Product, Integer> implements ProductDao {

}
