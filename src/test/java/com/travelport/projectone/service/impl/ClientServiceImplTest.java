package com.travelport.projectone.service.impl;

import com.travelport.projectone.entity.Client;
import com.travelport.projectone.entity.Product;
import com.travelport.projectone.jpa.ClientJpaRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    ClientJpaRepository clientDao;

    @Test
    @DisplayName("Given new client When save client Then return client")
    void testSaveSuccess() {
        // Given (setup)
        var clientToSend = Mockito.mock(Client.class);

        Mockito.when(clientDao.save(eq(clientToSend))).thenReturn(clientToSend);

        // When (do actions)
        var result = clientService.save(clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());

        Mockito.verify(clientDao).save(eq(clientToSend));
    }

    @Test
    @DisplayName("Given new client id already exists When save client Then return empty")
    void testSaveExistingProductReturnEmpty() {
        // Given (setup)
        var clientToSend = Mockito.mock(Client.class);

        Mockito.when(clientDao.existsById(eq(clientToSend.getNif()))).thenReturn(true);

        // When (do actions)
        var result = clientService.save(clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Given null client When save client Then return empty")
    void testSaveNullProductReturnEmpty() {
        // Given (setup)
        Client clientToSend = null;

        // When (do actions)
        var result = clientService.save(clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao, Mockito.never()).save(any());
        Mockito.verify(clientDao, Mockito.never()).existsById(any());
    }

    @Test
    @DisplayName("Given new client When save client error Then return empty")
    void testSaveErrorReturnEmpty() {
        // Given (setup)
        var clientToSend = Mockito.mock(Client.class);

        Mockito.when(clientDao.save(eq(clientToSend))).thenThrow(new MockitoException("Error"));

        // When (do actions)
        var result = clientService.save(clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).save(eq(clientToSend));
    }

    @Test
    @DisplayName("Given a client does not exist When update client Then return empty")
    void testUpdateNonExistingClientReturnsEmpty() {
        // Given (setup)
        var idToSend = "1";
        var clientToSend = Mockito.mock(Client.class);
        clientToSend.setNif(idToSend);

        Mockito.when(clientDao.findById(eq(idToSend))).thenReturn(Optional.empty());

        // When (do actions)
        var result = clientService.update(idToSend, clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao, Mockito.never()).save(eq(clientToSend));
    }

    @Test
    @DisplayName("Given a client exists When update client with all properties Then return client")
    void testUpdateExistingClientWithAllPropertiesReturnsClient() {
        // Given (setup)
        var idToSend = "1";

        var existingClient = new Client();
        existingClient.setNif(idToSend);
        existingClient.setName("Old name");
        existingClient.setAddress("Old address");

        var clientToSend = new Client();
        clientToSend.setNif(idToSend);
        clientToSend.setName("New name");
        clientToSend.setAddress("new address");

        Mockito.when(clientDao.findById(eq(idToSend))).thenReturn(Optional.of(existingClient));
        Mockito.when(clientDao.save(eq(clientToSend))).thenReturn(clientToSend);

        // When (do actions)
        var result = clientService.update(idToSend, clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(clientToSend.getNif(), result.get().getNif());
        assertEquals(clientToSend.getName(), result.get().getName());
        assertEquals(clientToSend.getAddress(), result.get().getAddress());

        Mockito.verify(clientDao).save(eq(clientToSend));
    }

    @Test
    @DisplayName("Given a client exists When update client with no properties Then return client")
    void testUpdateExistingClientWithNoPropertiesReturnsClient() {
        // Given (setup)
        var idToSend = "1";

        var existingClient = new Client();
        existingClient.setNif(idToSend);
        existingClient.setName("Old name");
        existingClient.setAddress("Old address");

        var clientToSend = new Client();
        
        var updatedClient = new Client();
        updatedClient.setNif(existingClient.getNif());
        updatedClient.setName(existingClient.getName());
        updatedClient.setAddress(existingClient.getAddress());

        Mockito.when(clientDao.findById(eq(idToSend))).thenReturn(Optional.of(existingClient));
        Mockito.when(clientDao.save(eq(updatedClient))).thenReturn(updatedClient);

        // When (do actions)
        var result = clientService.update(idToSend, clientToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(updatedClient.getNif(), result.get().getNif());
        assertEquals(updatedClient.getName(), result.get().getName());
        assertEquals(updatedClient.getAddress(), result.get().getAddress());

        Mockito.verify(clientDao).findById(eq(idToSend));
        Mockito.verify(clientDao).save(eq(updatedClient));
    }

    @Test
    @DisplayName("Given a client exists When delete by id Then return true")
    void testDeleteExistingClientReturnsTrue() {
        // Given (setup)
        var idToSend = "1";

        Mockito.when(clientDao.existsById(eq(idToSend))).thenReturn(true);

        // When (do actions)
        var result = clientService.delete(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertTrue(result);

        Mockito.verify(clientDao).deleteById(eq(idToSend));
        Mockito.verify(clientDao).existsById(eq(idToSend));
    }

    @Test
    @DisplayName("Given a client does not exists When delete by id Then return false")
    void testDeleteNonExistingClientReturnsFalse() {
        // Given (setup)
        var idToSend = "1";

        Mockito.when(clientDao.existsById(eq(idToSend))).thenReturn(false);

        // When (do actions)
        var result = clientService.delete(idToSend);

        // Then (asserts & verify)
        assertNotNull(result);
        assertFalse(result);

        Mockito.verify(clientDao, Mockito.never()).deleteById(eq(idToSend));
        Mockito.verify(clientDao).existsById(eq(idToSend));
    }

    @Test
    @DisplayName("Given empty database When get all clients Then return empty list")
    void testFindAllEmpty() {
        // Given

        // When
        var result = clientService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findAll();
    }

    @Test
    @DisplayName("Given two clients exists When get all clients Then return size 2 list")
    void testFindAllWithResults() {
        // Given
        var client1 = Mockito.mock(Client.class);
        var client2 = Mockito.mock(Client.class);
        var clientList = new ArrayList<Client>();
        clientList.add(client1);
        clientList.add(client2);

        Mockito.when(clientDao.findAll()).thenReturn(clientList);

        // When
        var result = clientService.findAll();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        Mockito.verify(clientDao).findAll();
    }

    @Test
    @DisplayName("Given id 1 When get client by id Then return empty")
    void testFindByIdNoResults() {
        // Given
        var idToSend = "1";

        // When
        var result = clientService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findById(eq(idToSend));
    }

    @Test
    @DisplayName("Given id 1 When get client by id Then return Client")
    void testFindByIdWithResults() {
        // Given
        var idToSend = "1";
        var client = Mockito.mock(Client.class);

        Mockito.when(clientDao.findById(eq(idToSend))).thenReturn(Optional.of(client));

        // When
        var result = clientService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isPresent());

        Mockito.verify(clientDao).findById(eq(idToSend));
    }

    @Test
    @DisplayName("Given null id When get client by id Then return empty")
    void testFindByIdWithNullParams() {
        // Given
        String idToSend = null;

        // When
        var result = clientService.findById(idToSend);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(clientDao).findById(any());
    }
}
