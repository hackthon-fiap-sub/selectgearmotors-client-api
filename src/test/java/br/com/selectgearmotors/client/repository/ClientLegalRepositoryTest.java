package br.com.selectgearmotors.client.repository;

import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clientlegal.ClientLegalEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientLegalRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
class ClientLegalRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientLegalRepository clientLegalRepository;

    @Autowired
    private ClientRepository clientRepository;

    private ClientEntity clientEntity;

    private ClientLegalEntity clientLegalEntity;

    private Faker faker = new Faker();

    private ClientEntity getClient(ClientTypeEntity clientType) {
        return ClientEntity.builder()
                .name(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .pic("hhh")
                .description("Coca-Cola")
                .clientTypeEntity(clientType)
                .build();
    }

    private ClientTypeEntity getClientType() {
        return ClientTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    private ClientLegalEntity getClientLegal(ClientEntity clientEntity) {
        return ClientLegalEntity.builder()
                .id(1l)
                .companyId("12345678000100")
                .clientEntity(clientEntity)
                .build();
    }

    private ClientLegalEntity getClientLegalCompanySize(ClientEntity clientEntity) {
        return ClientLegalEntity.builder()
                .id(1l)
                .companyId("123456780001000")
                .clientEntity(clientEntity)
                .build();
    }

    @BeforeEach
    void setUp() {
        clientLegalRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        ClientTypeEntity clientTypeEntity = clientTypeRepository.save(getClientType());
        this.clientEntity = clientRepository.save(getClient(clientTypeEntity));
    }

    @Test
    void should_store_a_client() {
        log.info("Setting up test data...");

        ClientLegalEntity clientLegal = getClientLegal(this.clientEntity);
        var clientLegalEntitySaved = clientLegalRepository.save(clientLegal);

        assertThat(clientLegalEntitySaved).isNotNull();
        assertThat(clientLegalEntitySaved.getId()).isNotNull();
        assertThat(clientLegalEntitySaved.getCompanyId()).isEqualTo(clientLegal.getCompanyId());
    }

    @Test
    void should_find_client_by_id() {
        log.info("Setting up test data...");
        ClientLegalEntity clientLegal = getClientLegal(this.clientEntity);
        var clientLegalEntitySaved = clientLegalRepository.save(clientLegal);

        Optional<ClientLegalEntity> foundClient = clientLegalRepository.findById(clientLegalEntitySaved.getId());
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getCompanyId()).isEqualTo(foundClient.get().getCompanyId());
    }

    @Test
    void should_find_all_clients() {
        log.info("Cleaning up database...");

        ClientLegalEntity clientLegal = getClientLegal(this.clientEntity);
        var clientLegalEntitySaved = clientLegalRepository.save(clientLegal);

        Iterable<ClientLegalEntity> clients = clientLegalRepository.findAll();
        List<ClientLegalEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
        assertThat(clientList).extracting(ClientLegalEntity::getCompanyId).contains(clientLegalEntitySaved.getCompanyId());
    }

    @Test
    void should_delete_all_clients() {
        log.info("Cleaning up database...");
        Iterable<ClientLegalEntity> clients = clientLegalRepository.findAll();
        assertThat(clients).isEmpty();
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        ClientLegalEntity fromDb = clientLegalRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfClients_whenFindAll_thenReturnAllClients() {
        ClientLegalEntity clientLegal = getClientLegal(this.clientEntity);
        clientLegalRepository.save(clientLegal);

        Iterable<ClientLegalEntity> clients = clientLegalRepository.findAll();
        List<ClientLegalEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientLegalEntity clientLegal = getClientLegal(this.clientEntity);

        clientLegalRepository.save(clientLegal);

    }
}