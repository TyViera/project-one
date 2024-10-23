package com.travelport.projectone.persistence.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProductDaoImpl {

    private EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("myPersistenceUnit");

}
