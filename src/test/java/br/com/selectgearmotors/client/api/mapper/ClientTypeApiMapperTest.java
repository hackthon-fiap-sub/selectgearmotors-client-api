package br.com.selectgearmotors.client.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientTypeRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientTypeResponse;
import br.com.selectgearmotors.client.application.api.mapper.ClientTypeApiMapper;
import br.com.selectgearmotors.client.core.domain.ClientType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientTypeApiMapperTest {

    private final ClientTypeApiMapper mapper = Mappers.getMapper(ClientTypeApiMapper.class);

    @Test
    void testFromRequest() {
        // Arrange
        ClientTypeRequest request = new ClientTypeRequest();
        request.setName("Electronics");

        // Act
        ClientType clientType = mapper.fromRequest(request);

        // Assert
        assertNotNull(clientType);
        assertEquals("Electronics", clientType.getName());
    }

    @Test
    void testFromEntity() {
        // Arrange
        ClientType clientType = new ClientType();
        clientType.setId(1L);
        clientType.setName("Electronics");

        // Act
        ClientTypeResponse response = mapper.fromEntity(clientType);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getName());
    }

    @Test
    void testMap() {
        // Arrange
        ClientType clientType1 = new ClientType();
        clientType1.setId(1L);
        clientType1.setName("Electronics");

        ClientType clientType2 = new ClientType();
        clientType2.setId(2L);
        clientType2.setName("Furniture");

        List<ClientType> productCategories = Arrays.asList(clientType1, clientType2);

        // Act
        List<ClientTypeResponse> responses = mapper.map(productCategories);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).getId());
        assertEquals("Electronics", responses.get(0).getName());

        assertEquals(2L, responses.get(1).getId());
        assertEquals("Furniture", responses.get(1).getName());
    }
}
