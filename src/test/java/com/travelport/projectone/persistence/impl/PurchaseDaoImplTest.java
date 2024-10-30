package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Purchase;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class PurchaseDaoImplTest {

    @InjectMocks
    PurchaseDaoImpl purchaseDao;

    @Mock
    EntityManager em;

    @Spy
    HashMap<Integer, Purchase> purchases;
}
