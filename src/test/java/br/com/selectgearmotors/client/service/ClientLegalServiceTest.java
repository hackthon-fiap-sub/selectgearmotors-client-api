package br.com.selectgearmotors.client.service;

import br.com.selectgearmotors.client.application.database.mapper.ClientMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.clientlegal.*;
import br.com.selectgearmotors.client.core.ports.out.ClientLegalRepositoryPort;
import br.com.selectgearmotors.client.core.service.ClientLegalService;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
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
class ClientLegalServiceTest {

    @InjectMocks
    ClientLegalService clientLegalService;

    @Mock
    ClientLegalRepositoryPort clientLegalRepository;

    @Mock
    ClientRepository repository;

    @Mock
    ClientMapper mapper;

    @Mock
    CreateClientLegalPort createClientLegalPort;

    @Mock
    DeleteClientLegalPort deleteClientLegalPort;

    @Mock
    FindByIdClientLegalPort findByIdClientLegalPort;

    @Mock
    FindClientLegalsPort findClientLegalsPort;

    @Mock
    UpdateClientLegalPort updateClientLegalPort;

    private ClientLegal getClientLegal(Client client) {
        return ClientLegal.builder()
                .companyId("12345678000100")
                .clientId(client.getId())
                .build();
    }

    private ClientLegalEntity getClientLegalEntity(ClientEntity clientEntity) {
        return ClientLegalEntity.builder()
                .companyId("12345678000100")
                .clientEntity(clientEntity)
                .build();
    }

    private ClientEntity getClientEntity(ClientTypeEntity clientTypeEntity) {
        return ClientEntity.builder()
                .name("Bebida")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
                .description("Coca-Cola")
                .clientTypeEntity(clientTypeEntity)
                .build();
    }

    private Client getClient(ClientType clientType) {
        return Client.builder()
                .name("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .pic("hhh")
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
        List<ClientLegal> clients = new ArrayList<>();
        List<ClientLegalEntity> listEntity = new ArrayList<>();

        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientLegal clientLegal = getClientLegal(client);
        ClientLegalEntity clientLegalEntity = getClientLegalEntity(getClientEntity(getClientTypeEntity()));

        clients.add(clientLegal);

        listEntity.add(clientLegalEntity);

        when(clientLegalService.findAll()).thenReturn(clients);

        List<ClientLegal> clientList = clientLegalService.findAll();

        assertNotNull(clientList);
    }

    @Test
    void getClientByIdTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientLegal clientLegal = getClientLegal(client);
        when(clientLegalService.findById(1L)).thenReturn(clientLegal);

        ClientLegal clientLegalResponse = clientLegalService.findById(1L);

        assertEquals("12345678000100", clientLegalResponse.getCompanyId());
    }

    @Test
    void getFindClientByShortIdTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientLegal clientLegal = getClientLegal(client);
        when(clientLegalService.findById(1L)).thenReturn(clientLegal);

        ClientLegal result = clientLegalService.findById(1L);

        assertEquals("12345678000100", result.getCompanyId());
    }

    @Test
    void createClientTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientLegal clientLegal = getClientLegal(client);
        when(clientLegalService.save(clientLegal)).thenReturn(clientLegal);

        ClientLegal save = clientLegalService.save(clientLegal);

        assertNotNull(save);
        //verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientLegal clientLegal = getClientLegal(getClient(getClientType()));
        clientLegal.setCompanyId("1".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(clientLegalRepository).save(clientLegal);

        assertThrows(DataException.class, () -> {
            clientLegalRepository.save(clientLegal);
        });
    }

    @Test
    void testRemove_Exception() {
        Long clientId = 99L;

        boolean result = clientLegalService.remove(clientId);
        assertFalse(result);
        verify(clientLegalRepository, never()).remove(clientId);
    }

    @Test
    void testCreateClient() {
        ClientLegal clientLegal = getClientLegal(getClient(getClientType()));
        when(createClientLegalPort.save(clientLegal)).thenReturn(clientLegal);

        ClientLegal result = createClientLegalPort.save(clientLegal);

        assertNotNull(result);
        assertEquals("12345678000100", result.getCompanyId());
    }

    @Test
    void testDeleteClient() {
        Long clientId = 1L;
        when(deleteClientLegalPort.remove(clientId)).thenReturn(true);

        boolean result = deleteClientLegalPort.remove(clientId);

        assertTrue(result);
    }

    @Test
    void testFindByIdClient() {
        ClientLegal clientLegal = getClientLegal(getClient(getClientType()));
        when(findByIdClientLegalPort.findById(1L)).thenReturn(clientLegal);

        ClientLegal result = findByIdClientLegalPort.findById(1L);

        assertNotNull(result);
        assertEquals("12345678000100", result.getCompanyId());
    }

    @Test
    void testFindClients() {
        ClientLegal clientLegal = getClientLegal(getClient(getClientType()));
        List<ClientLegal> clients = new ArrayList<>();
        clients.add(clientLegal);

        when(findClientLegalsPort.findAll()).thenReturn(clients);
        List<ClientLegal> result = findClientLegalsPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateClient() {
        Long clientId = 1L;
        ClientLegal clientLegal = getClientLegal(getClient(getClientType()));

        when(updateClientLegalPort.update(clientId, clientLegal)).thenReturn(clientLegal);
        ClientLegal result = updateClientLegalPort.update(clientId, clientLegal);

        assertNotNull(result);
        assertEquals("12345678000100", result.getCompanyId());
    }
}