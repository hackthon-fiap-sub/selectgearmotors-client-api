package br.com.selectgearmotors.client.entity;

import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientEntityTest {

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        Client client = new Client();
        client.setName("Updated Name");
        client.setDescription("Updated Description");

        ClientEntity entity = new ClientEntity();
        entity.setId(2L);
        entity.setName("Old Name");
        entity.setDescription("Old Description");

        // Act
        entity.update(id, entity);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals("Old Name", entity.getName());
        assertEquals("Old Description", entity.getDescription());
    }

    @Test
    void testGettersAndSetters() {
        ClientEntity entity = new ClientEntity();
        entity.setId(1L);
        entity.setName("Old Name");
        entity.setDescription("Old Description");

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Old Name");
    }

    @Disabled
    void testToString() {
        ClientTypeEntity clientCategory = new ClientTypeEntity();
        clientCategory.setId(1L);
        clientCategory.setName("Bebida");

        ClientEntity client = new ClientEntity();
        client.setId(1L);
        client.setCode("d7d19a26-846f-4808-818b-ffc3495be7bb");
        client.setName("Old Name");
        client.setDescription("Old Description");

        String expected = "ClientEntity(id=1, code=d7d19a26-846f-4808-818b-ffc3495be7bb, name=Old Name, pic=Old Pic, description=Old Description, clientCategory=ClientCategoryEntity(id=1, name=Bebida), restaurant=RestaurantEntity(id=1, name=Test Restaurant, cnpj=12.345.678/0001-99))";
        assertThat(client).hasToString(expected);
    }

    @Disabled
    void testEqualsAndHashCode() {
        ClientEntity client1 = ClientEntity.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .build();

        ClientEntity client2 = ClientEntity.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .build();

        assertThat(client2).isEqualTo(client2);
        assertThat(client1).hasSameHashCodeAs(client2);
    }
}
