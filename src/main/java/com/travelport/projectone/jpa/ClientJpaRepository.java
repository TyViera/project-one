package com.travelport.projectone.jpa;

import com.travelport.projectone.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<Client, String> {}