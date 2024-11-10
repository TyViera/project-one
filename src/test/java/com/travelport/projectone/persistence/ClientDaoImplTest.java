package com.travelport.projectone.persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

import com.travelport.projectone.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class ClientDaoImplTest {
    @InjectMocks
    private ClientDaoImpl clientDao;

    @Mock
    private EntityManager entityManager;

    MockMvc mockMvc;

    @BeforeEach
    void initEachTest() {
        System.out.println("Starting a test...");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("Cleaning up after test...");
    }

    @Test
    void saveClientTest() {
        Client client = new Client();
        client.setNif("12345678A");

        clientDao.save(client);

        Mockito.verify(entityManager).persist(client);

        System.out.println("saveClientTest - Client NIF: " + client.getNif());
    }

    @Test
    void listClientsTest() {
        String jpql = "FROM Client";
        var mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(eq(jpql), eq(Client.class))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(new Client(), new Client()));

        List<Client> result = clientDao.list();

        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(entityManager).createQuery(eq(jpql), eq(Client.class));

        System.out.println("listClientsTest - Number of clients: " + result.size());
    }

    @Test
    void getClientByNifTest() {
        String nif = "12345678A";
        Client client = new Client();
        Mockito.when(entityManager.find(Client.class, nif)).thenReturn(client);

        Optional<Client> result = clientDao.getClientByNif(nif);

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
        Mockito.verify(entityManager).find(Client.class, nif);

        System.out.println("getClientByNifTest - Client found with NIF: " + nif);
    }

    @Test
    void updateClientTest() {
        Client client = new Client();
        client.setNif("12345678A");

        clientDao.update(client);

        Mockito.verify(entityManager).merge(client);

        System.out.println("updateClientTest - Updated client with NIF: " + client.getNif());
    }

    @Test
    void deleteClientByNifTest() {
        String nif = "12345678A";
        Client client = new Client();
        Mockito.when(entityManager.find(Client.class, nif)).thenReturn(client);

        Optional<String> result = clientDao.deleteByNif(nif);

        assertTrue(result.isPresent());
        assertEquals(nif, result.get());
        Mockito.verify(entityManager).remove(client);

        System.out.println("deleteClientByNifTest - Client with NIF " + nif + " deleted.");
    }
}
