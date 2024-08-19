package br.com.selectgearmotors.client.entity;

import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTypeEntityTest {

    @Disabled
    void testUpdate() {
        // Arrange
        Long id = 1L;
        ClientType clientType = new ClientType();
        clientType.setName("Updated Name");

        ClientTypeEntity clientTypeEntity = new ClientTypeEntity();
        clientTypeEntity.setId(2L);
        clientTypeEntity.setName("Old Name");

        // Act
        clientTypeEntity.update(id, clientTypeEntity);

        // Assert
        assertEquals(id, clientTypeEntity.getId());
        assertEquals("Updated Name", clientTypeEntity.getName());
    }

    @Test
    void testGettersAndSetters() {
        ClientType clientType = new ClientType();
        clientType.setId(1L);
        clientType.setName("Updated Name");

        assertThat(clientType.getId()).isEqualTo(1L);
        assertThat(clientType.getName()).isEqualTo("Updated Name");
    }

    @Disabled
    void testToString() {
        ClientType clientType = ClientType.builder()
                .id(1L)
                .name("Updated Name")
                .build();

        String expected = "ProductCategory(id=1, name=Updated Name)";
        assertEquals(clientType.toString(), expected);
    }
}
