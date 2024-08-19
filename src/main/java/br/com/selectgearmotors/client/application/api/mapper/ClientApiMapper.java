package br.com.selectgearmotors.client.application.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientResponse;
import br.com.selectgearmotors.client.core.domain.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientApiMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "pic", target = "pic")
    @Mapping(source = "socialId", target = "socialId")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "dataProcessingConsent", target = "dataProcessingConsent")
    @Mapping(source = "clientTypeId", target = "clientTypeId")
    Client fromRequest(ClientRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "socialId", source = "socialId")
    ClientResponse fromEntity(Client client);

    List<ClientResponse> map(List<Client> clients);

}
