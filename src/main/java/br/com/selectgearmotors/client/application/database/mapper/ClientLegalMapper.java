package br.com.selectgearmotors.client.application.database.mapper;

import br.com.selectgearmotors.client.commons.util.CompanyIdFormatter;
import br.com.selectgearmotors.client.core.domain.ClientLegal;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = CompanyIdFormatter.class)
public interface ClientLegalMapper {

    @Mapping(source = "companyId", target = "companyId", qualifiedByName = "formatCompanyId")
    @Mapping(source = "clientId", target = "clientEntity.id")
    ClientLegalEntity fromModelTpEntity(ClientLegal clientLegal);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ClientLegal fromEntityToModel(ClientLegalEntity productCategoryEntity);

    List<ClientLegal> map(List<ClientLegalEntity> productCategoryEntities);
}
