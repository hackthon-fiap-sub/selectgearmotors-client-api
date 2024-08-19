package br.com.selectgearmotors.client.application.database.mapper;

import br.com.selectgearmotors.client.core.domain.ClientType;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientTypeMapper {

    @Mapping(source = "name", target = "name")
    ClientTypeEntity fromModelTpEntity(ClientType clientType);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ClientType fromEntityToModel(ClientTypeEntity productCategoryEntity);

    List<ClientType> map(List<ClientTypeEntity> productCategoryEntities);
}
