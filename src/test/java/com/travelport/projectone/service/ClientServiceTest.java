package com.travelport.projectone.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projectone.entities.Client;
import com.travelport.projectone.jpa.ClientJpaRepository;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

  @InjectMocks
  private ClientServiceImpl clientService;

  @Mock
  private ClientJpaRepository clientJpaRepository;

  private Client client;

  @BeforeEach
  void setUp() {
    client = new Client();
    client.setId("87654321B");
    client.setName("Martin");
    client.setAddress("Av Inventada 2");
  }

  @Test
  void testCreateClient() {
    Mockito.when(clientJpaRepository.save(client)).thenReturn(client);
    assertEquals(client, clientService.createClient(client));
  }

  @Test
  void testGetAllClients() {
    Client client2 = new Client();
    client2.setId("12345678A");
    client2.setName("John");
    client2.setAddress("Main St 1");

    when(clientJpaRepository.findAll()).thenReturn(Arrays.asList(client, client2));

    List<Client> clients = clientService.getAllClients();
    assertEquals(2, clients.size());
    assertEquals("Martin", clients.get(0).getName());
    assertEquals("John", clients.get(1).getName());
  }

  @Test
  void testGetClientById() {
    when(clientJpaRepository.findById("87654321B")).thenReturn(Optional.of(client));

    Client foundClient = clientService.getClientById("87654321B");
    assertEquals("Martin", foundClient.getName());
  }

  @Test
  void testGetClientByIdNotFound() {
    when(clientJpaRepository.findById("invalidId")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      clientService.getClientById("invalidId");
    });

    assertEquals("Client not found with id: invalidId", exception.getMessage());
  }

  @Test
  void testUpdateClients() {
    Client updatedClient = new Client();
    updatedClient.setName("Edu");
    updatedClient.setAddress("Av Inventada 1");

    when(clientJpaRepository.findById("87654321B")).thenReturn(Optional.of(client));
    when(clientJpaRepository.save(client)).thenReturn(client);

    Client updated = clientService.updateClients("87654321B", updatedClient);
    assertEquals("Edu", updated.getName());
    assertEquals("Av Inventada 1", updated.getAddress());
  }

  @Test
  void testUpdateClientsNotFound() {
    Client updatedClient = new Client();
    updatedClient.setName("Edu");
    updatedClient.setAddress("Av Inventada 1");

    when(clientJpaRepository.findById("invalidId")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      clientService.updateClients("invalidId", updatedClient);
    });

    assertEquals("Client not found with id: invalidId", exception.getMessage());
  }

  @Test
  void testDeleteClients() {
    doNothing().when(clientJpaRepository).deleteById("87654321B");

    assertDoesNotThrow(() -> clientService.deleteClients("87654321B"));
    verify(clientJpaRepository, times(1)).deleteById("87654321B");
  }
}
