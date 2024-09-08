package br.com.selectgearmotors.client.domain;

import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class ClientPhysicalTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        ClientPhysical entity = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("123.456.789-00", entity.getSocialId());
        assertEquals(1L, entity.getClientId());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        ClientPhysical entity = new ClientPhysical();

        // Act
        entity.setId(1L);
        entity.setSocialId("123.456.789-00");
        entity.setClientId(1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("123.456.789-00", entity.getSocialId());
        assertEquals(1L, entity.getClientId());
    }

    @Test
    public void testToString() {
        // Arrange
        ClientPhysical entity = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ClientPhysical"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("socialId=123.456.789-00"));
        assertTrue(toString.contains("clientId=1"));
    }

    @Test
    public void testHashCode() {
        // Arrange
        ClientPhysical entity1 = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);
        ClientPhysical entity2 = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Disabled
    public void testEquals() {
        // Arrange
        ClientPhysical entity1 = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);
        ClientPhysical entity2 = new ClientPhysical(1L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L);
        ClientPhysical entity3 = new ClientPhysical(2L, "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 2L);

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}
