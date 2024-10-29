package com.travelport.jpa.impl;

import com.travelport.entities.Product;
import com.travelport.jpa.ProductDao;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoImpl extends AbstractEntityDaoImpl<Product, Integer> implements ProductDao {

}
