package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.persistence.ClientDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext // JPA
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Client client) {
        entityManager.persist(client);
    }

    @Override
    @Transactional
    public List<Client> list() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<Client> getClientById(Integer id) {
        return Optional.ofNullable(entityManager.find(Client.class, id));
    }

    @Override
    @Transactional
    public void update(Client client) {
        entityManager.merge(client);
    }

    @Override
    @Transactional
    public Optional<Integer> deleteById(Integer id) {
        Client client = entityManager.find(Client.class, id);
        if (client != null) {
            entityManager.remove(client);
            return Optional.of(id);
        }
        return Optional.empty();
    }

}
