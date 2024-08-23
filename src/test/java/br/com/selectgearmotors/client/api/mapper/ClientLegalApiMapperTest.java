package br.com.selectgearmotors.client.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientLegalRequest;
import br.com.selectgearmotors.client.application.api.mapper.ClientLegalApiMapper;
import br.com.selectgearmotors.client.application.api.mapper.ClientLegalApiMapperImpl;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientLegalApiMapperTest {

    private ClientLegalApiMapper mapper;

    @BeforeEach
    void setUp() {
        // Supondo que você esteja usando MapStruct com Spring
        // Se não estiver usando Spring, pode instanciar o mapper diretamente
        mapper = new ClientLegalApiMapperImpl();
    }

    @Test
    void testFromRequest() {
        // Arrange
        ClientLegalRequest request = new ClientLegalRequest();
        request.setCompanyId("12.345.678/0001-00");

        // Act
        ClientLegal clientLegal = mapper.fromRequest(request);

        // Assert
        assertNotNull(clientLegal, "O objeto clientLegal não deveria ser nulo");
        assertEquals("12.345.678/0001-00", clientLegal.getCompanyId(), "O companyId deveria ser '12.345.678/0001-00'");
    }

    @Test
    void testFromRequestWithNullCompanyId() {
        // Arrange
        ClientLegalRequest request = new ClientLegalRequest();
        request.setCompanyId(null);

        // Act
        ClientLegal clientLegal = mapper.fromRequest(request);

        // Assert
        assertNotNull(clientLegal, "O objeto clientLegal não deveria ser nulo, mesmo com companyId nulo");
        assertNull(clientLegal.getCompanyId(), "O companyId deveria ser nulo");
    }
}
