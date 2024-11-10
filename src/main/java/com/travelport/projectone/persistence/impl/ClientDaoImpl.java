//package com.travelport.persistence.impl;
//
//import com.travelport.entities.Client;
//import com.travelport.persistence.ClientDao;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import java.util.Optional;
//import java.util.Map;
//import java.util.HashMap;
//
//@Component
//@Transactional
//public class ClientDaoImpl implements ClientDao {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private final Map<Integer, Client> cache;
//
//    public ClientDaoImpl(){cache = new HashMap<>();}
//
//    @Override
//    @Transactional(readOnly = true, propagation = Propagation.NEVER)
//    public List<Client> findAll() {
//        return entityManager.createQuery("FROM Client", Client.class).getResultList();
//    }
//
//    @Override
//    public Optional<Client> getClientById(Integer id) {
//        var client = cache.computeIfAbsent(id, x -> entityManager.find(Client.class, x));
//        return Optional.ofNullable(client);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void update(Client client) {
//        entityManager.merge(client);
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public Optional<Integer> deleteById(Integer id) {
//        return getClientById(id)
//            .map(
//                client -> {
//                    entityManager.remove(client);
//                    return id;
//                });
//    }
//
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED)
//    public void save(Client client) {
//        if (client.getId() != null) {
//            entityManager.persist(client);
//        }
//    }
//}
