package br.com.selectgearmotors.client.domain;

import br.com.selectgearmotors.client.core.domain.ClientLegal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientLegalTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        ClientLegal entity = new ClientLegal(1L, "12.345.678/0001-00", 1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("12.345.678/0001-00", entity.getCompanyId());
        assertEquals(1L, entity.getClientId());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        ClientLegal entity = new ClientLegal();

        // Act
        entity.setId(1L);
        entity.setCompanyId("12.345.678/0001-00");
        entity.setClientId(1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("12.345.678/0001-00", entity.getCompanyId());
        assertEquals(1L, entity.getClientId());
    }

    @Test
    public void testToString() {
        // Arrange
        ClientLegal entity = new ClientLegal(1L, "12.345.678/0001-00", 1L);

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ClientLegal"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("companyId=12.345.678/0001-00"));
        assertTrue(toString.contains("clientId=1"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        ClientLegal entity1 = new ClientLegal(1L, "12.345.678/0001-00", 1L);
        ClientLegal entity2 = new ClientLegal(1L, "12.345.678/0001-00", 1L);

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void testEquals() {
        // Arrange
        ClientLegal entity1 = new ClientLegal(1L, "12.345.678/0001-00", 1L);
        ClientLegal entity2 = new ClientLegal(1L, "12.345.678/0001-00", 1L);
        ClientLegal entity3 = new ClientLegal(2L, "98.765.432/0001-00", 2L);

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}
