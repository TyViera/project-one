package com.travelport.jpa;

import java.util.List;
import java.util.Optional;

public interface AbstractEntityDao<E, K> {

    void save(E entity);

    List<E> list();

    Optional<E> findById(K id);

    void update(E entity);

    void delete(K id);

}
