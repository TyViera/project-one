package com.travelport.projectone.service;

import com.travelport.entities.Client;
import com.travelport.persistence.ClientDao;
import com.travelport.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    void findAllClients(){
        //Set up - given
        Client client1 = new Client();
        client1.setName("Pepito Doe");
        client1.setAddress("Carrer Aiguablava");
        client1.setNif("123123123Z");

        Client client2 = new Client();
        client2.setName("Manuela Doe");
        client2.setAddress("Carrer Aiguablava 34");
        client2.setNif("123134123S");


        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        when(clientDao.findAll()).thenReturn(clients);

        //Do something - when
        List<Client> result = clientService.findAll();

        //Asserts -Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(clients, result);
        verify(clientDao, times(1)).findAll();
    }

    @Test
    void findClientById_ClientExists() {
        // Set up - given
        int clientId = 1;
        Client client = new Client();
        client.setId(clientId);
        client.setName("Pepito Doe");

        when(clientDao.findById(clientId)).thenReturn(Optional.of(client));

        // Do something - when
        Optional<Client> result = clientService.findById(clientId);

        // Asserts - Then
        assertTrue(result.isPresent());
        assertEquals(client, result.get());
        verify(clientDao, times(1)).findById(clientId);
    }

    @Test
    void findClientById_ClientDoesNotExist() {
        // Set up - given
        int clientId = 1;
        when(clientDao.findById(clientId)).thenReturn(Optional.empty());

        // Do something - when
        Optional<Client> result = clientService.findById(clientId);

        // Assert - Then
        assertFalse(result.isPresent());
        verify(clientDao, times(1)).findById(clientId);
    }

    @Test
    void deleteClientById() {
        // Set up - given
        int clientId = 1;

        // Do something - when
        clientService.deleteById(clientId);

        // Assert - Then
        verify(clientDao, times(1)).deleteById(clientId);
    }

    @Test
    void saveClient() {
        // Set up - given
        Client client = new Client();
        client.setName("Pepito Doe");

        when(clientDao.save(client)).thenReturn(client);

        // Do something - when
        Client result = clientService.save(client);

        // Asserts - Then
        assertNotNull(result);
        assertEquals(client, result);
        verify(clientDao, times(1)).save(client);
    }

    @Test
    void updateClient_ClientExists() {
        // Set up - given
        int clientId = 1;
        Client clientToUpdate = new Client();
        clientToUpdate.setName("Updated Name");
        clientToUpdate.setAddress("Updated Address");

        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setName("Original Name");
        existingClient.setAddress("Original Address");

        when(clientDao.findById(clientId)).thenReturn(Optional.of(existingClient));

        // Do something - when
        Client result = clientService.update(clientId, clientToUpdate);

        // Asserts - Then
        assertNotNull(result);
        assertEquals(clientToUpdate.getName(), result.getName());
        assertEquals(clientToUpdate.getAddress(), result.getAddress());
        verify(clientDao, times(1)).findById(clientId);
        verify(clientDao, times(1)).update(existingClient);
    }

    @Test
    void updateClient_ClientDoesNotExist() {
        // Set up - given
        int clientId = 1;
        Client clientToUpdate = new Client();
        clientToUpdate.setName("Updated Name");

        when(clientDao.findById(clientId)).thenReturn(Optional.empty());

        // Do something - when
        Client result = clientService.update(clientId, clientToUpdate);

        // Asserts - Then
        assertNull(result);
        verify(clientDao, times(1)).findById(clientId);
        verify(clientDao, times(0)).update(any(Client.class));
    }

}
