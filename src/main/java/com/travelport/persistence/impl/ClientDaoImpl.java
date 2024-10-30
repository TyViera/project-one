package com.travelport.persistence.impl;

import com.travelport.entities.Client;
import com.travelport.persistence.ClientDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class ClientDaoImpl extends AbstractEntityDaoImpl<Client, String> implements ClientDao {

}
