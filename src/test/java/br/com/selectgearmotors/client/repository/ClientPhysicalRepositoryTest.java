package br.com.selectgearmotors.client.repository;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientphysical.ClientPhysicalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientPhysicalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class ClientPhysicalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientPhysicalRepository clientPhysicalRepository;

    @Autowired
    private ClientRepository clientRepository;

    private ClientEntity clientEntity;

    private ClientPhysicalEntity clientPhysicalEntity;

    private Faker faker = new Faker();

    private ClientEntity getClient(ClientTypeEntity clientType) {
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

    private ClientTypeEntity getClientType() {
        return ClientTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    private ClientPhysicalEntity getClientPhysical(ClientEntity clientEntity) {
        return ClientPhysicalEntity.builder()
                .id(1l)
                .socialId("35511965081")
                .clientEntity(clientEntity)
                .build();
    }

    private ClientPhysicalEntity getClientPhysicalCompanySize(ClientEntity clientEntity) {
        return ClientPhysicalEntity.builder()
                .id(1l)
                .socialId("35511965081")
                .clientEntity(clientEntity)
                .build();
    }

    @BeforeEach
    void setUp() {
        clientPhysicalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        ClientTypeEntity clientTypeEntity = clientTypeRepository.save(getClientType());
        this.clientEntity = clientRepository.save(getClient(clientTypeEntity));
    }

    @Disabled
    void should_store_a_client() {
        log.info("Setting up test data...");

        ClientPhysicalEntity clientPhysical = getClientPhysical(this.clientEntity);
        var clientPhysicalEntitySaved = clientPhysicalRepository.save(clientPhysical);

        assertThat(clientPhysicalEntitySaved).isNotNull();
        assertThat(clientPhysicalEntitySaved.getId()).isNotNull();
        assertThat(clientPhysicalEntitySaved.getSocialId()).isEqualTo(clientPhysical.getSocialId());
    }

    @Disabled
    void should_find_client_by_id() {
        log.info("Setting up test data...");
        ClientPhysicalEntity clientPhysical = getClientPhysical(this.clientEntity);
        var clientPhysicalEntitySaved = clientPhysicalRepository.save(clientPhysical);

        Optional<ClientPhysicalEntity> foundClient = clientPhysicalRepository.findById(clientPhysicalEntitySaved.getId());
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getSocialId()).isEqualTo(foundClient.get().getSocialId());
    }

    @Disabled
    void should_find_all_clients() {
        log.info("Cleaning up database...");

        ClientPhysicalEntity clientPhysical = getClientPhysical(this.clientEntity);
        var clientPhysicalEntitySaved = clientPhysicalRepository.save(clientPhysical);

        Iterable<ClientPhysicalEntity> clients = clientPhysicalRepository.findAll();
        List<ClientPhysicalEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
        assertThat(clientList).extracting(ClientPhysicalEntity::getSocialId).contains(clientPhysicalEntitySaved.getSocialId());
    }

    @Disabled
    void should_delete_all_clients() {
        log.info("Cleaning up database...");
        Iterable<ClientPhysicalEntity> clients = clientPhysicalRepository.findAll();
        assertThat(clients).isEmpty();
    }

    @Disabled
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        ClientPhysicalEntity fromDb = clientPhysicalRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    void givenSetOfClients_whenFindAll_thenReturnAllClients() {
        ClientPhysicalEntity clientPhysical = getClientPhysical(this.clientEntity);
        clientPhysicalRepository.save(clientPhysical);

        Iterable<ClientPhysicalEntity> clients = clientPhysicalRepository.findAll();
        List<ClientPhysicalEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        ClientPhysicalEntity clientPhysical = getClientPhysical(this.clientEntity);
        clientPhysicalRepository.save(clientPhysical);
    }
}