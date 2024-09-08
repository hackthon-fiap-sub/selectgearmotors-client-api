package br.com.selectgearmotors.client.application.api.mapper;

import br.com.selectgearmotors.client.application.api.dto.request.ClientPhysicalRequest;
import br.com.selectgearmotors.client.application.api.dto.response.ClientPhysicalResponse;
import br.com.selectgearmotors.client.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface ClientPhysicalApiMapper {

    @Mapping(source = "socialId", target = "socialId")
    @Mapping(source = "socialIdDispatchDate", target = "socialIdDispatchDate")
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "documentDistrict", target = "documentDistrict")
    @Mapping(source = "documentDispatchDate", target = "documentDispatchDate")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "clientId", target = "clientId")
    ClientPhysical fromRequest(ClientPhysicalRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(source = "socialId", target = "socialId", qualifiedByName = "formatSocialId")
    ClientPhysicalResponse fromEntity(ClientPhysical clientPhysical);

   List<ClientPhysicalResponse> map(List<ClientPhysical> clientPhysicals);
}
