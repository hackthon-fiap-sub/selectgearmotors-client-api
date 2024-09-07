package br.com.selectgearmotors.client.factory;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import com.github.javafaker.Faker;

import java.util.UUID;

public class ObjectFactory {
    public static ObjectFactory instance;
    private final Faker faker = new Faker();

    private ObjectFactory() {}

    public static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public ClientTypeEntity getClientType() {
        return ClientTypeEntity.builder()
                .id(1L)
                .name("Pragmatico")
                .build();
    }

    public ClientEntity getClient(ClientTypeEntity clientType) {
        return ClientEntity.builder()
                .name(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .clientTypeEntity(clientType)
                .build();
    }

    public ClientLegalEntity getClientLegal(ClientEntity clientEntity) {
        return ClientLegalEntity.builder()
                .id(1L)
                .companyId("12345678000100")
                .clientEntity(clientEntity)
                .build();
    }

    public ClientPhysicalEntity getClientPhysical(ClientEntity clientEntity) {
        return ClientPhysicalEntity.builder()
                .id(1L)
                .socialId("96738231032")
                .clientEntity(clientEntity)
                .build();
    }
}