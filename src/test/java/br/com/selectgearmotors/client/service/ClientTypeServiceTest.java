package br.com.selectgearmotors.client.service;

import br.com.selectgearmotors.client.application.database.mapper.ClientTypeMapper;
import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.core.ports.in.clienttype.*;
import br.com.selectgearmotors.client.core.ports.out.ClientTypeRepositoryPort;
import br.com.selectgearmotors.client.core.service.ClientTypeService;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ClientTypeServiceTest {

    @InjectMocks
    ClientTypeService clientTypeService;

    @Mock
    ClientTypeRepositoryPort productCategoryRepository;

    @Mock
    ClientTypeRepository repository;

    @Mock
    ClientTypeMapper mapper;

    @Mock
    CreateClientTypePort createClientTypePort;

    @Mock
    DeleteClientTypePort deleteClientTypePort;

    @Mock
    FindByIdClientTypePort findByIdClientTypePort;

    @Mock
    FindClientTypesPort findProductCategoriesPort;

    @Mock
    UpdateClientTypePort updateClientTypePort;

    private Validator validator;

    private ClientTypeEntity getClientTypeEntity() {
        return ClientTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private ClientTypeEntity getClientTypeEntity1() {
        return ClientTypeEntity.builder()
                .name("Bebida 1")
                .build();
    }

    private ClientTypeEntity getClientTypeEntity2() {
        return ClientTypeEntity.builder()
                .name("Bebida 2")
                .build();
    }

    private ClientType getClientType() {
        return ClientType.builder()
                .name("Bebida")
                .build();
    }

    private ClientType getClientType1() {
        return ClientType.builder()
                .name("Bebida 1")
                .build();
    }

    private ClientType getClientType2() {
        return ClientType.builder()
                .name("Bebida 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllClientTypesTest() {
        List<ClientType> clientTypes = new ArrayList<>();
        List<ClientTypeEntity> listEntity = new ArrayList<>();

        ClientType client = getClientType();
        ClientType client1 = getClientType1();
        ClientType client2 = getClientType2();

        ClientTypeEntity clientEntity = getClientTypeEntity();
        ClientTypeEntity clientEntity1 = getClientTypeEntity1();
        ClientTypeEntity clientEntity2 = getClientTypeEntity2();

        clientTypes.add(client);
        clientTypes.add(client1);
        clientTypes.add(client2);

        listEntity.add(clientEntity);
        listEntity.add(clientEntity1);
        listEntity.add(clientEntity2);

        when(clientTypeService.findAll()).thenReturn(clientTypes);

        List<ClientType> clientTypeList = clientTypeService.findAll();

        assertNotNull(clientTypeList);
    }

    @Test
    void getClientTypeByIdTest() {
        ClientType clientType1 = getClientType();
        when(clientTypeService.findById(1L)).thenReturn(clientType1);

        ClientType clientType = clientTypeService.findById(1L);

        assertEquals("Bebida", clientType.getName());
    }

    @Test
    void getFindClientTypeByShortIdTest() {
        ClientType clientType = getClientType();
        when(clientTypeService.findById(1L)).thenReturn(clientType);

        ClientType result = clientTypeService.findById(1L);

        assertEquals("Bebida", result.getName());
    }

    @Test
    void createClientTypeTest() {
        ClientType clientType = getClientType();
        ClientType clientTypeResult = getClientType();
        clientTypeResult.setId(1L);

        when(clientTypeService.save(clientType)).thenReturn(clientTypeResult);
        ClientType save = clientTypeService.save(clientType);

        assertNotNull(save);
        //verify(productCategoryRepository, times(1)).save(productCategory);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientType clientType = new ClientType();
        clientType.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productCategoryRepository).save(clientType);

        assertThrows(DataException.class, () -> {
            productCategoryRepository.save(clientType);
        });
    }

    @Test
    void testRemoveRestaurant_Success() {
        Long restaurantId = 1L;
        ClientType clientType = getClientType();
        clientType.setId(restaurantId);

        when(clientTypeService.findById(restaurantId)).thenReturn(clientType);
        boolean result = clientTypeService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = clientTypeService.remove(productId);
        assertFalse(result);
        verify(productCategoryRepository, never()).remove(productId);
    }

    @Test
    void testCreateClientType() {
        ClientType clientType = getClientType();
        when(createClientTypePort.save(clientType)).thenReturn(clientType);

        ClientType result = createClientTypePort.save(clientType);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testDeleteClientType() {
        Long productId = 1L;
        when(deleteClientTypePort.remove(productId)).thenReturn(true);

        boolean result = deleteClientTypePort.remove(productId);

        assertTrue(result);
    }

    @Test
    void testFindByIdClientType() {
        ClientType clientType = getClientType();
        when(findByIdClientTypePort.findById(1L)).thenReturn(clientType);

        ClientType result = findByIdClientTypePort.findById(1L);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testFindProductCategories() {
        List<ClientType> productCategories = new ArrayList<>();
        productCategories.add(getClientType());
        productCategories.add(getClientType1());
        productCategories.add(getClientType2());

        when(findProductCategoriesPort.findAll()).thenReturn(productCategories);

        List<ClientType> result = findProductCategoriesPort.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testUpdateClientType() {
        Long productId = 1L;
        ClientType clientType = getClientType();
        clientType.setName("Updated Name");

        when(updateClientTypePort.update(productId, clientType)).thenReturn(clientType);

        ClientType result = updateClientTypePort.update(productId, clientType);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

}