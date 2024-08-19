package br.com.selectgearmotors.client.entity;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientPhysicalEntityTest {

    @Test
    public void testUpdate() {
        // Arrange
        ClientEntity clientEntity1 = new ClientEntity();
        ClientEntity clientEntity2 = new ClientEntity();
        ClientPhysicalEntity originalEntity = new ClientPhysicalEntity(1L, "123.456.789-00", clientEntity1);
        ClientPhysicalEntity updatedEntity = new ClientPhysicalEntity(2L, "987.654.321-00", clientEntity2);

        // Act
        originalEntity.update(2L, updatedEntity);

        // Assert
        assertEquals(2L, originalEntity.getId());
        assertEquals("987.654.321-00", originalEntity.getSocialId());
        assertEquals(clientEntity2, originalEntity.getClientEntity());
    }
}
