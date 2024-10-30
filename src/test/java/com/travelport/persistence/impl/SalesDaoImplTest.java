package com.travelport.persistence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalesDaoImplTest {

    SalesDaoImpl salesDao;

    @BeforeEach
    void initEachTest() {
        salesDao = new SalesDaoImpl();
    }

    @Test
    void selectFormDbANifThatExist(){
        salesDao = new SalesDaoImpl();
        var nif = "49831796L";

        var result = salesDao.findByClientNif(nif);
        assert result != null;
    }
}