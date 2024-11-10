package com.travelport.projectone.service;

import com.travelport.projectone.config.DataBaseConfig;
import com.travelport.projectone.config.JpaRepositoryConfig;
import com.travelport.projectone.config.WebAppConfig;
import com.travelport.projectone.config.WebAppInitializer;
import com.travelport.projectone.controller.ClientController;
import com.travelport.projectone.entities.Client;
import com.travelport.projectone.jpa.ClientJpaRepository;
import com.travelport.projectone.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {
        DataBaseConfig.class,
        JpaRepositoryConfig.class,
        WebAppConfig.class,
        WebAppInitializer.class,
        ClientController.class,
        Client.class,
        ClientService.class
})
class ClientServiceImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @Mock
    private ClientJpaRepository clientDao;

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl(clientDao);
    Client client = new Client();

    MockMvc mockMvc;
    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test //have to update test
    void testUpdate(){
        when(clientDao.findById(1)).thenReturn(Optional.ofNullable(client));

        Client updatedClient = new Client();

        updatedClient.setName("David Saumell");
        updatedClient.setDNI("12345678A");
        updatedClient.setAddress("Carrer Sant Joan Bosco");

        Optional<Client> result = clientService.update(1, updatedClient);

        assertEquals(updatedClient.getAddress(), result.get().getAddress());
        assertEquals(updatedClient.getName(), result.get().getName());
        assertEquals(updatedClient.getDNI(), result.get().getDNI());
        assertEquals(updatedClient.getId(), result.get().getId());
    }

    @Test
    void testFindAll() {
        var client1 = Mockito.mock(Client.class);
        var client2 = Mockito.mock(Client.class);
        var clientList = new ArrayList<Client>();
        clientList.add(client1);
        clientList.add(client2);

        when(clientDao.findAll()).thenReturn(clientList);

        var result = clientService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

        Mockito.verify(clientDao).findAll();
    }

    @Test
    void testDeleteClient() {
        var id = 1;

        Mockito.when(clientDao.existsById(eq(id))).thenReturn(true);

        Mockito.verify(clientDao).deleteById(eq(id));
        Mockito.verify(clientDao).existsById(eq(id));
    }

    @Test
    void testSave() {

        var clientToSend = Mockito.mock(Client.class);

        Mockito.when(clientDao.save(eq(clientToSend))).thenReturn(clientToSend);

        var result = clientService.save(clientToSend);
        assertNotNull(result);
        assertTrue(result.isPresent());

        Mockito.verify(clientDao).save(eq(clientToSend));
    }
}