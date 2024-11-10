package com.travelport.projectone.persistence;

import com.travelport.projectone.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDaoImpl implements ClientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void save(Client client) {
        entityManager.persist(client);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NEVER)
    public List<Client> list() {
                                            //JPQL
        return entityManager.createQuery("FROM Client", Client.class).getResultList();
    }

    @Override
    public Optional<Client> getClientByNif(String nif) {
        return Optional.ofNullable(entityManager.find(Client.class, nif));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void update(Client client) {
        entityManager.merge(client);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Optional<String> deleteByNif(String nif) {
        return getClientByNif(nif).map(client -> {entityManager.remove(client);return nif;});
    }

}
