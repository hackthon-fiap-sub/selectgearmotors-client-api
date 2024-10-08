package br.com.selectgearmotors.client.application.database.mapper;

import br.com.selectgearmotors.client.commons.util.SocialIdFormatter;
import br.com.selectgearmotors.client.core.domain.ClientPhysical;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = SocialIdFormatter.class)
public interface ClientPhysicalMapper {

    @Mapping(source = "socialId", target = "socialId", qualifiedByName = "formatSocialId")
    @Mapping(source = "clientId", target = "clientEntity.id")
    ClientPhysicalEntity fromModelTpEntity(ClientPhysical clientPhysical);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ClientPhysical fromEntityToModel(ClientPhysicalEntity productCategoryEntity);

    List<ClientPhysical> map(List<ClientPhysicalEntity> productCategoryEntities);
}
