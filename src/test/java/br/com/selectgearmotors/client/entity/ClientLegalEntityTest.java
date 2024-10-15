package br.com.selectgearmotors.client.entity;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientLegalEntityTest {

    @Test
    public void testUpdate() {
        // Arrange
        ClientEntity clientEntity1 = new ClientEntity();
        ClientEntity clientEntity2 = new ClientEntity();
        ClientLegalEntity originalEntity = new ClientLegalEntity(1L, "RGR", "RGF C", "98.765.432/0001-00" , LocalDate.now(), clientEntity1);
        ClientLegalEntity updatedEntity = new ClientLegalEntity(2L, "RGR", "RGF C", "98.765.432/0001-00" , LocalDate.now(), clientEntity2);

        // Act
        originalEntity.update(2L, updatedEntity);

        // Assert
        assertEquals(2L, originalEntity.getId());
        assertEquals("98.765.432/0001-00", originalEntity.getCompanyId());
        assertEquals(clientEntity2, originalEntity.getClientEntity());
    }

    @Test
    public void testConstructor() {
        // Arrange
        ClientEntity clientEntity = new ClientEntity();

        // Act
        ClientLegalEntity entity = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("12.345.678/0001-00", entity.getCompanyId());
        assertEquals(clientEntity, entity.getClientEntity());
    }

    @Test
    public void testToString() {
        // Arrange
        ClientEntity clientEntity = new ClientEntity();
        ClientLegalEntity entity = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ClientLegalEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("companyId=12.345.678/0001-00"));
        assertTrue(toString.contains("clientEntity="));
    }

    @Disabled
    public void testHashCode() {
        // Arrange
        ClientEntity clientEntity = new ClientEntity();
        ClientLegalEntity entity1 = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);
        ClientLegalEntity entity2 = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Disabled
    public void testEquals() {
        // Arrange
        ClientEntity clientEntity = new ClientEntity();
        ClientLegalEntity entity1 = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);
        ClientLegalEntity entity2 = new ClientLegalEntity(1L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);
        ClientLegalEntity entity3 = new ClientLegalEntity(2L, "RGR", "RGF C", "12.345.678/0001-00" , LocalDate.now(), clientEntity);

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}
