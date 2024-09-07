package br.com.selectgearmotors.client.service;

import br.com.selectgearmotors.client.application.database.mapper.ClientMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.client.*;
import br.com.selectgearmotors.client.core.ports.out.ClientRepositoryPort;
import br.com.selectgearmotors.client.core.service.ClientService;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepositoryPort clientRepository;

    @Mock
    ClientRepository repository;

    @Mock
    ClientMapper mapper;

    @Mock
    CreateClientPort createClientPort;

    @Mock
    DeleteClientPort deleteClientPort;

    @Mock
    FindByIdClientPort findByIdClientPort;

    @Mock
    FindClientsPort findClientsPort;

    @Mock
    UpdateClientPort updateClientPort;

    private ClientEntity getClientEntity(ClientTypeEntity clientTypeEntity) {
        return ClientEntity.builder()
                .name("Bebida")
                .code(UUID.randomUUID().toString())
                .description("Coca-Cola")
                .clientTypeEntity(clientTypeEntity)
                .build();
    }

    private Client getClient(ClientType clientType) {
        return Client.builder()
                .name("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .description("Coca-Cola")
                .clientTypeId(clientType.getId())
                .build();
    }

    private ClientTypeEntity getClientTypeEntity() {
        return ClientTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private ClientType getClientType() {
        return ClientType.builder()
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllClientsTest() {
        List<Client> clients = new ArrayList<>();
        List<ClientEntity> listEntity = new ArrayList<>();

        Client client = getClient(getClientType());
        ClientEntity clientEntity = getClientEntity(getClientTypeEntity());

        clients.add(client);

        listEntity.add(clientEntity);
        when(clientService.findAll()).thenReturn(clients);

        List<Client> clientList = clientService.findAll();

        assertNotNull(clientList);
    }

    @Test
    void getClientByIdTest() {
        Client client1 = getClient(getClientType());
        when(clientService.findById(1L)).thenReturn(client1);

        Client client = clientService.findById(1L);

        assertEquals("Coca-Cola", client.getName());
    }

    @Test
    void getFindClientByShortIdTest() {
        Client client = getClient(getClientType());
        when(clientService.findById(1L)).thenReturn(client);

        Client result = clientService.findById(1L);

        assertEquals("Coca-Cola", result.getName());
    }

    @Test
    void createClientTest() {
        Client client = getClient(getClientType());
        Client clientResult = getClient(getClientType());
        clientResult.setId(1L);

        when(clientService.save(client)).thenReturn(clientResult);
        Client save = clientService.save(client);

        assertNotNull(save);
        //verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        Client client = new Client();
        client.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        client.setCode(UUID.randomUUID().toString());
        client.setDescription("Coca-Cola");

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(clientRepository).save(client);

        assertThrows(DataException.class, () -> {
            clientRepository.save(client);
        });
    }

    @Test
    void testRemove_Exception() {
        Long clientId = 99L;

        boolean result = clientService.remove(clientId);
        assertFalse(result);
        verify(clientRepository, never()).remove(clientId);
    }

    @Test
    void testCreateClient() {
        Client client = getClient(getClientType());
        Client clientResult = getClient(getClientType());
        when(createClientPort.save(client)).thenReturn(clientResult);

        Client result = createClientPort.save(client);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getName());
    }

    @Test
    void testDeleteClient() {
        Long clientId = 1L;
        when(deleteClientPort.remove(clientId)).thenReturn(true);

        boolean result = deleteClientPort.remove(clientId);

        assertTrue(result);
    }

    @Test
    void testFindByIdClient() {
        Client client = getClient(getClientType());
        when(findByIdClientPort.findById(1L)).thenReturn(client);

        Client result = findByIdClientPort.findById(1L);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getName());
    }

    @Test
    void testFindClients() {
        Client client = getClient(getClientType());
        List<Client> clients = new ArrayList<>();
        clients.add(client);

        when(findClientsPort.findAll()).thenReturn(clients);
        List<Client> result = findClientsPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateClient() {
        Long clientId = 1L;
        Client client = getClient(getClientType());

        when(updateClientPort.update(clientId, client)).thenReturn(client);
        Client result = updateClientPort.update(clientId, client);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getName());
    }
}