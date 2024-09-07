package br.com.selectgearmotors.client.application.database.mapper;

import br.com.selectgearmotors.client.core.domain.Client;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dataProcessingConsent", target = "dataProcessingConsent")
    @Mapping(source = "clientTypeId", target = "clientTypeEntity.id")
    @Mapping(source = "mediaId", target = "mediaEntity.id")
    ClientEntity fromModelTpEntity(Client client);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "mobile", source = "mobile")
    @Mapping(target = "clientTypeId", source = "clientTypeEntity.id")
    @Mapping(target = "mediaId", source = "mediaEntity.id")
    Client fromEntityToModel(ClientEntity clientEntity);

    List<Client> map(List<ClientEntity> clientEntities);
}
