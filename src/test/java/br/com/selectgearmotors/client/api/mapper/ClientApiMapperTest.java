package br.com.selectgearmotors.client.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientResponse;
import br.com.selectgearmotors.client.application.api.mapper.ClientApiMapper;
import br.com.selectgearmotors.client.core.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientApiMapperTest {

    private ClientApiMapper clientApiMapper;

    @BeforeEach
    public void setUp() {
        clientApiMapper = Mappers.getMapper(ClientApiMapper.class);
    }

    @Test
    void testFromRequest() {
        // Arrange
        ClientRequest request = new ClientRequest();
        request.setName("Client Name");
        request.setDescription("Client Description");
        request.setPic("pic.jpg");

        // Act
        Client client = clientApiMapper.fromRequest(request);

        // Assert
        assertNotNull(client);
        assertEquals(request.getName(), client.getName());
        assertEquals(request.getDescription(), client.getDescription());
        assertEquals(request.getPic(), client.getPic());
    }

    @Test
    void testFromEntity() {
        // Arrange
        Client product = new Client();
        product.setId(1L);
        product.setCode("P001");
        product.setName("Client Name");
        product.setDescription("Client Description");
        product.setPic("pic.jpg");

        // Act
        ClientResponse response = clientApiMapper.fromEntity(product);

        // Assert
        assertNotNull(response);
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getCode(), response.getCode());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getDescription(), response.getDescription());
        assertEquals(product.getPic(), response.getPic());
    }

    @Test
    void testMap() {
        // Arrange
        Client product1 = new Client();
        product1.setId(1L);
        product1.setCode("P001");
        product1.setName("Client 1");
        product1.setDescription("Description 1");
        product1.setPic("pic1.jpg");

        Client product2 = new Client();
        product2.setId(2L);
        product2.setCode("P002");
        product2.setName("Client 2");
        product2.setDescription("Description 2");
        product2.setPic("pic2.jpg");

        List<Client> products = Arrays.asList(product1, product2);

        // Act
        List<ClientResponse> responses = clientApiMapper.map(products);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        ClientResponse response1 = responses.get(0);
        assertEquals(product1.getId(), response1.getId());
        assertEquals(product1.getCode(), response1.getCode());
        assertEquals(product1.getName(), response1.getName());
        assertEquals(product1.getDescription(), response1.getDescription());
        assertEquals(product1.getPic(), response1.getPic());

        ClientResponse response2 = responses.get(1);
        assertEquals(product2.getId(), response2.getId());
        assertEquals(product2.getCode(), response2.getCode());
        assertEquals(product2.getName(), response2.getName());
        assertEquals(product2.getDescription(), response2.getDescription());
        assertEquals(product2.getPic(), response2.getPic());
    }
}
