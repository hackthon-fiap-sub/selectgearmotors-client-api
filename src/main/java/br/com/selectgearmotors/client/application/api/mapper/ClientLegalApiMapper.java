package br.com.selectgearmotors.client.application.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientLegalRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientLegalResponse;
import br.com.selectgearmotors.client.commons.util.CompanyIdFormatter;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CompanyIdFormatter.class)
public interface ClientLegalApiMapper {

    @Mapping(source = "socialName", target = "socialName")
    @Mapping(source = "fantasyName", target = "fantasyName")
    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "foundationDate", target = "foundationDate")
    @Mapping(source = "clientId", target = "clientId")
    ClientLegal fromRequest(ClientLegalRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(source = "companyId", target = "companyId", qualifiedByName = "revertCompanyId")
    ClientLegalResponse fromEntity(ClientLegal clientLegal);

    List<ClientLegalResponse> map(List<ClientLegal> clientLegals);
}
