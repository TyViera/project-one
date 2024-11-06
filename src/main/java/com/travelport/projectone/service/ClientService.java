package com.travelport.projectone.service;

import com.travelport.projectone.entity.Client;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Optional<Client> save(@RequestBody Client client);

    Optional<Client> update(@PathVariable("id") String id, @RequestBody Client client);

    Boolean delete(@PathVariable("id") String id);

    List<Client> findAll();

    Optional<Client> findById(@PathVariable("id") String id);

}
