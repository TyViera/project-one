package com.travelport.persistence.impl;

import com.travelport.entities.Client;
import com.travelport.persistence.ClientDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Component
public class ClientDaoImpl implements ClientDao {
    @PersistenceContext//JPA
    private EntityManager entityManager;

    private final Map<Integer, Client> cache;

    public ClientDaoImpl(){cache=new HashMap<>();}

    @Override
    public Client save(Client client){
        entityManager.persist(client);
        return client;
    }

    @Override
    public List<Client> findAll(){
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    public Optional<Client> findById(Integer id){
        var foundClient=cache.computeIfAbsent(id, x -> entityManager.find(Client.class, x));
        return Optional.ofNullable(foundClient);
    }

    @Override
    public void update(Client client){
        entityManager.merge(client);
    }

    @Override
    public Optional<Integer> deleteById(Integer id){
        return findById(id).map(
                client -> {
                    entityManager.remove(client);
                    return id;
                }
        );
    }

}
