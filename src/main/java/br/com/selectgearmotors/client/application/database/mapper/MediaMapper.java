package br.com.selectgearmotors.client.application.database.mapper;

import br.com.selectgearmotors.client.core.domain.Media;
import br.com.selectgearmotors.client.infrastructure.entity.media.MediaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "mobile", target = "mobile")
    @Mapping(source = "pic", target = "pic")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "dataProcessingConsent", target = "dataProcessingConsent")
    @Mapping(source = "mediaTypeId", target = "mediaTypeEntity.id")
    MediaEntity fromModelTpEntity(Media media);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "mediaTypeId", source = "mediaTypeEntity.id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "mobile", source = "mobile")
    Media fromEntityToModel(MediaEntity mediaEntity);

    List<Media> map(List<MediaEntity> mediaEntities);
}
