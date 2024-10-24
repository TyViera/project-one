package com.travelport.projectone.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

public class Sales {


    @EmbeddedId
    private SalesPk salesId;


    @Embeddable
    class SalesPk{

        private Integer clientId;
        private Integer productId;
    }

}
