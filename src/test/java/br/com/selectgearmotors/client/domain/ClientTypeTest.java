package br.com.selectgearmotors.client.domain;

import br.com.selectgearmotors.client.core.domain.ClientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTypeTest {

    private ClientType clientType;
    private ClientType anotherClientType;

    @BeforeEach
    void setUp() {
        clientType = ClientType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        anotherClientType = ClientType.builder()
                .id(2L)
                .name("Snacks")
                .build();
    }

    @Test
    void testGetters() {
        assertEquals(1L, clientType.getId());
        assertEquals("Beverages", clientType.getName());
    }

    @Test
    void testSetters() {
        clientType.setId(2L);
        clientType.setName("Snacks");

        assertEquals(2L, clientType.getId());
        assertEquals("Snacks", clientType.getName());
    }

    @Test
    void testEquals() {
        ClientType copy = ClientType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        assertEquals(clientType, copy);
    }

    @Test
    void testHashCode() {
        ClientType copy = ClientType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        assertEquals(clientType.hashCode(), copy.hashCode());
    }

    @Disabled
    void testToString() {
        String expected = "ProductCategory(id=1, name=Beverages)";
        assertEquals(expected, clientType.toString());
    }

    @Test
    void testUpdate() {
        clientType.update(3L, anotherClientType);

        assertEquals(3L, clientType.getId());
        assertEquals("Snacks", clientType.getName());
    }

    @Test
    void testNoArgsConstructor() {
        ClientType newClientType = new ClientType();
        assertNotNull(newClientType);
    }

    @Test
    void testAllArgsConstructor() {
        ClientType newClientType = new ClientType(4L, "Frozen Foods");
        assertEquals(4L, newClientType.getId());
        assertEquals("Frozen Foods", newClientType.getName());
    }
}
