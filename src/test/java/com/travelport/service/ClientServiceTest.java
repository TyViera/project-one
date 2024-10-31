package com.travelport.service;

import com.travelport.entities.Client;
import com.travelport.persistence.ClientDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    // Under Test Components
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    void saveClientWithValidClient_ShouldCallDaoSave() {
        // Given
        Client clientToSave = new Client();
        clientToSave.setNif("12345678A");
        clientToSave.setName("John Doe");
        clientToSave.setAddress("123 Main Street");

        // When
        clientService.saveClient(clientToSave);

        // Then
        verify(clientDao, times(1)).save(clientToSave);
    }

    @Test
    void saveClientWithNullClient_ShouldThrowException() {
        // Given
        Client clientToSave = null;

        // When + Then
        assertThrows(IllegalArgumentException.class, () -> clientService.saveClient(clientToSave));
    }

    @Test
    void findAll_shouldReturnListOfClients() {
        // Given
        Client client1 = new Client();
        client1.setNif("12345678A");
        client1.setName("John Doe");
        client1.setAddress("123 Main St");

        Client client2 = new Client();
        client2.setNif("87654321B");
        client2.setName("Jane Smith");
        client2.setAddress("456 Elm St");

        List<Client> clients = Arrays.asList(client1, client2);
        when(clientDao.list()).thenReturn(clients);

        // When
        List<Client> result = clientService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(clients, result);
        verify(clientDao, times(1)).list();
    }


    @Test
    void findClientByNif_ClientExists_ShouldReturnClient() {
        String nif = "12345678A";
        Client client = new Client();
        client.setNif(nif);
        client.setName("John Doe");
        client.setAddress("123 Main St");
        when(clientDao.findById(nif)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.findClientByNif(nif);

        assertTrue(result.isPresent());
        assertEquals(client, result.get());
        verify(clientDao, times(1)).findById(nif);
    }

    @Test
    void findClientByNif_ClientDoesNotExist_ShouldReturnEmpty() {
        String nif = "12345678A";
        when(clientDao.findById(nif)).thenReturn(Optional.empty());

        Optional<Client> result = clientService.findClientByNif(nif);

        assertFalse(result.isPresent());
        verify(clientDao, times(1)).findById(nif);
    }

    @Test
    void updateClientNotNull_ShouldCallDaoUpdate() {
        Client clientToUpdate = new Client();
        clientToUpdate.setNif("12345678A");
        clientToUpdate.setName("John Doe");
        clientToUpdate.setAddress("123 Main St");

        clientService.updateClient(clientToUpdate);

        verify(clientDao, times(1)).save(clientToUpdate);
    }

    @Test
    void updateClientNull_ShouldThrowException() {
        Client clientToUpdate = null;

        assertThrows(IllegalArgumentException.class, () -> clientService.updateClient(clientToUpdate));

    }

    @Test
    void deleteClientByNif() {
        String nif = "12345678A";

        clientService.deleteClientByNif(nif);

        verify(clientDao, times(1)).delete(nif);
    }
}