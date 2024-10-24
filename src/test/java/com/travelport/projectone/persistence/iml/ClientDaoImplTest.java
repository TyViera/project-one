package com.travelport.projectone.persistence.iml;

import com.travelport.projectone.entities.Client;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientDaoImplTest {

    @InjectMocks
    ClientDaoImpl clientDao;

    @Mock
    EntityManager em;

    @Spy
    HashMap<String, Client> cache;

    @BeforeAll
    static void beforeAll() { System.out.println("Before All..."); }

    @BeforeEach
    void initEachTest() { System.out.println("Init test..."); }

    @AfterEach
    void afterEachTest() { System.out.println("Cleaning up..."); }

    @ParameterizedTest
    @MethodSource("provideStringsForIsClientExist")
    void givenParamValues_whenIsClientExists_ThenReturnAssertValue(String nif, boolean assertValue) {
        var result = clientDao.clientExists(nif);
        assertEquals(assertValue, result);
    }

    @Test
    void getClientByNifTest() {
        var nifTOoFind = "Y12345678X";

        ReflectionTestUtils.setField(clientDao, "cache", cache);
        Mockito.when(em.find(Client.class, nifTOoFind)).thenReturn(new Client());

        assertTrue(cache.isEmpty()); //Before the call, the cache must be empty
        var result = clientDao.getClientByNif(nifTOoFind);
        System.out.println("RESULT: " + result);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertFalse(cache.isEmpty());

        Mockito.verify(em).find(Client.class, nifTOoFind);
    }

    static Stream<Arguments> provideStringsForIsClientExist() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("argument", false),
                Arguments.of("ana", false),
                Arguments.of("Ana García", true)
        );
    }
}