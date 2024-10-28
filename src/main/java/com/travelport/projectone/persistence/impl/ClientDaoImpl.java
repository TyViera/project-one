package com.travelport.projectone.persistence.impl;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.persistence.ClientDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager em;

    private final Map<String, Client> cache;

    public ClientDaoImpl() { cache = new HashMap<>(); }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Client client) { em.persist(client); }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Client> list() {
        var query = em.createQuery("from Client", Client.class);
        return query.getResultList();
    }

    public boolean clientExists(String nif) {
        return (nif != null && em.find(Client.class, nif) != null);
    }

    @Override
    public Optional<Client> getClientByNif(String nif) {
        var foundClient = cache.computeIfAbsent(nif, x -> em.find(Client.class, x));
        return Optional.ofNullable(foundClient);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Client client) {
        em.merge(client);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteByNif(String nif) {
        getClientByNif(nif)
                .map(
                        client -> {
                            em.remove(client);
                            return nif;
                        });
    }
}
