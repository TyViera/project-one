package com.travelport.projectone.service.impl;

import com.travelport.projectone.entities.Client;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    EntityManager entityManager;

    @Spy
    HashMap<String, Client> cache;


    @BeforeEach
    void setUp() {
        System.out.println("Init test...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up...");
    }

    @Test
    void getClients() {
        List<Client> clients = clientService.getClients();
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsClientExists")
    void findByNif(String nif, boolean expected) {
        var result = clientService.findByNif(nif);
        assertNotNull(result);
        assertEquals(result.isPresent(), expected);
    }

    static Stream<Arguments> provideStringsForIsClientExists() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("argument", false),
                Arguments.of("123456789", true),
                Arguments.of("Ana García", false)
        );
    }

    @Test
    void deleteByNif() {
        var nifToDelete = "123456789";
        var listBeforeDelete = clientService.getClients();
        clientService.deleteByNif(nifToDelete);
        var listAfterDelete = clientService.getClients();
        assertTrue(listBeforeDelete.size() > listAfterDelete.size());
    }

    @Test
    void save() {
        var listBeforeSave = clientService.getClients();
        clientService.save(new Client());
        var listAfterSave = clientService.getClients();
        assertTrue(listBeforeSave.size() < listAfterSave.size());
    }

    @Test
    void update() {
    }

    @Test
    void seePastSales() {
        var nif = "123456789";
        var listSales = clientService.seePastSales(nif);
        assertNotNull(listSales);
        assertTrue(listSales.size() >= 0);
    }
}