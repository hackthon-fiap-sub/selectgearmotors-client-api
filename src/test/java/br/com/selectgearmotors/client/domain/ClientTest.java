package br.com.selectgearmotors.client.domain;

import br.com.selectgearmotors.client.core.domain.Client;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    @Test
    void testGettersAndSetters() {
        Client client = new Client();
        client.setId(1L);
        client.setCode("CODE123");
        client.setName("Client Name");
        client.setDescription("Client Description");
        client.setPic("pic.jpg");

        assertEquals(1L, client.getId());
        assertEquals("CODE123", client.getCode());
        assertEquals("Client Name", client.getName());
        assertEquals("Client Description", client.getDescription());
        assertEquals("pic.jpg", client.getPic());
    }

    @Test
    void testBuilder() {
        Client client = Client.builder()
                .id(1L)
                .code("CODE123")
                .name("Client Name")
                .description("Client Description")
                .pic("pic.jpg")
                .build();

        assertEquals(1L, client.getId());
        assertEquals("CODE123", client.getCode());
        assertEquals("Client Name", client.getName());
        assertEquals("Client Description", client.getDescription());
        assertEquals("pic.jpg", client.getPic());
    }

    @Test
    void testUpdate() {
        Client client = new Client();
        client.setId(1L);
        client.setCode("CODE123");
        client.setName("Client Name");
        client.setDescription("Client Description");
        client.setPic("pic.jpg");

        Client newClient = new Client();
        newClient.setCode("NEWCODE");
        newClient.setName("New Client Name");
        newClient.setDescription("New Client Description");
        newClient.setPic("newpic.jpg");

        client.update(2L, newClient);

        assertEquals(2L, client.getId());
        assertEquals("NEWCODE", client.getCode());
        assertEquals("New Client Name", client.getName());
        assertEquals("New Client Description", client.getDescription());
        assertEquals("newpic.jpg", client.getPic());
    }

    @Test
    void testNoArgsConstructor() {
        Client client = new Client();
        assertNotNull(client);
    }
}