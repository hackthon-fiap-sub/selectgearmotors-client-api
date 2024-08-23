package br.com.selectgearmotors.client.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientPhysicalRequest;
import br.com.selectgearmotors.client.application.api.mapper.ClientPhysicalApiMapper;
import br.com.selectgearmotors.client.application.api.mapper.ClientPhysicalApiMapperImpl;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientPhysicalApiMapperTest {

    private ClientPhysicalApiMapper mapper;

    @BeforeEach
    void setUp() {
        // Supondo que você esteja usando MapStruct com Spring
        // Se não estiver usando Spring, pode instanciar o mapper diretamente
        mapper = new ClientPhysicalApiMapperImpl();
    }

    @Test
    void testFromRequest() {
        // Arrange
        ClientPhysicalRequest request = new ClientPhysicalRequest();
        request.setSocialId("123.456.789-00");

        // Act
        ClientPhysical clientPhysical = mapper.fromRequest(request);

        // Assert
        assertNotNull(clientPhysical, "O objeto clientPhysical não deveria ser nulo");
        assertEquals("123.456.789-00", clientPhysical.getSocialId(), "O companyId deveria ser '12.345.678/0001-00'");
    }

    @Test
    void testFromRequestWithNullCompanyId() {
        // Arrange
        ClientPhysicalRequest request = new ClientPhysicalRequest();
        request.setSocialId(null);

        // Act
        ClientPhysical clientPhysical = mapper.fromRequest(request);

        // Assert
        assertNotNull(clientPhysical, "O objeto clientPhysical não deveria ser nulo, mesmo com companyId nulo");
        assertNull(clientPhysical.getSocialId(), "O companyId deveria ser nulo");
    }
}
