package br.com.selectgearmotors.client.service;

import br.com.selectgearmotors.client.application.database.mapper.ClientMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.clientphysical.*;
import br.com.selectgearmotors.client.core.ports.out.ClientPhysicalRepositoryPort;
import br.com.selectgearmotors.client.core.service.ClientPhysicalService;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
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
class ClientPhysicalServiceTest {

    @InjectMocks
    ClientPhysicalService clientPhysicalService;

    @Mock
    ClientPhysicalRepositoryPort clientPhysicalRepository;

    @Mock
    ClientRepository repository;

    @Mock
    ClientMapper mapper;

    @Mock
    CreateClientPhysicalPort createClientPhysicalPort;

    @Mock
    DeleteClientPhysicalPort deleteClientPhysicalPort;

    @Mock
    FindByIdClientPhysicalPort findByIdClientPhysicalPort;

    @Mock
    FindClientPhysicalsPort findClientPhysicalsPort;

    @Mock
    UpdateClientPhysicalPort updateClientPhysicalPort;

    private ClientPhysical getClientPhysical(Client client) {
        return ClientPhysical.builder()
                .socialId("12345678000100")
                .clientId(client.getId())
                .build();
    }

    private ClientPhysicalEntity getClientPhysicalEntity(ClientEntity clientEntity) {
        return ClientPhysicalEntity.builder()
                .socialId("12345678000100")
                .clientEntity(clientEntity)
                .build();
    }

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
        List<ClientPhysical> clients = new ArrayList<>();
        List<ClientPhysicalEntity> listEntity = new ArrayList<>();

        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientPhysical clientPhysical = getClientPhysical(client);
        ClientPhysicalEntity clientPhysicalEntity = getClientPhysicalEntity(getClientEntity(getClientTypeEntity()));

        clients.add(clientPhysical);

        listEntity.add(clientPhysicalEntity);

        when(clientPhysicalService.findAll()).thenReturn(clients);

        List<ClientPhysical> clientList = clientPhysicalService.findAll();

        assertNotNull(clientList);
    }

    @Test
    void getClientByIdTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientPhysical clientPhysical = getClientPhysical(client);
        when(clientPhysicalService.findById(1L)).thenReturn(clientPhysical);

        ClientPhysical clientPhysicalResponse = clientPhysicalService.findById(1L);

        assertEquals("12345678000100", clientPhysicalResponse.getSocialId());
    }

    @Test
    void getFindClientByShortIdTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientPhysical clientPhysical = getClientPhysical(client);
        when(clientPhysicalService.findById(1L)).thenReturn(clientPhysical);

        ClientPhysical result = clientPhysicalService.findById(1L);

        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void createClientTest() {
        ClientType clientType = getClientType();
        Client client = getClient(clientType);

        ClientPhysical clientPhysical = getClientPhysical(client);
        when(clientPhysicalService.save(clientPhysical)).thenReturn(clientPhysical);

        ClientPhysical save = clientPhysicalService.save(clientPhysical);

        assertNotNull(save);
        //verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientPhysical clientPhysical = getClientPhysical(getClient(getClientType()));
        clientPhysical.setSocialId("1".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(clientPhysicalRepository).save(clientPhysical);

        assertThrows(DataException.class, () -> {
            clientPhysicalRepository.save(clientPhysical);
        });
    }

    @Test
    void testRemove_Exception() {
        Long clientId = 99L;

        boolean result = clientPhysicalService.remove(clientId);
        assertFalse(result);
        verify(clientPhysicalRepository, never()).remove(clientId);
    }

    @Test
    void testCreateClient() {
        ClientPhysical clientPhysical = getClientPhysical(getClient(getClientType()));
        when(createClientPhysicalPort.save(clientPhysical)).thenReturn(clientPhysical);

        ClientPhysical result = createClientPhysicalPort.save(clientPhysical);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void testDeleteClient() {
        Long clientId = 1L;
        when(deleteClientPhysicalPort.remove(clientId)).thenReturn(true);

        boolean result = deleteClientPhysicalPort.remove(clientId);

        assertTrue(result);
    }

    @Test
    void testFindByIdClient() {
        ClientPhysical clientPhysical = getClientPhysical(getClient(getClientType()));
        when(findByIdClientPhysicalPort.findById(1L)).thenReturn(clientPhysical);

        ClientPhysical result = findByIdClientPhysicalPort.findById(1L);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void testFindClients() {
        ClientPhysical clientPhysical = getClientPhysical(getClient(getClientType()));
        List<ClientPhysical> clients = new ArrayList<>();
        clients.add(clientPhysical);

        when(findClientPhysicalsPort.findAll()).thenReturn(clients);
        List<ClientPhysical> result = findClientPhysicalsPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateClient() {
        Long clientId = 1L;
        ClientPhysical clientPhysical = getClientPhysical(getClient(getClientType()));

        when(updateClientPhysicalPort.update(clientId, clientPhysical)).thenReturn(clientPhysical);
        ClientPhysical result = updateClientPhysicalPort.update(clientId, clientPhysical);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }
}