package com.travelport.jpa.impl;

import com.travelport.entities.Client;
import com.travelport.jpa.ClientDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ClientDaoImpl extends AbstractEntityDaoImpl<Client, String> implements ClientDao {

}
