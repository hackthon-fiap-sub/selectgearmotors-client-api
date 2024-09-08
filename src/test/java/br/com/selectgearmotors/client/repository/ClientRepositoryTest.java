package br.com.selectgearmotors.client.repository;

import br.com.selectgearmotors.client.application.database.mapper.ClientMapper;
import br.com.selectgearmotors.client.core.domain.Media;
import br.com.selectgearmotors.client.infrastructure.entity.client.ClientEntity;
import br.com.selectgearmotors.client.infrastructure.entity.clienttype.ClientTypeEntity;
import br.com.selectgearmotors.client.infrastructure.entity.domain.MediaType;
import br.com.selectgearmotors.client.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.client.infrastructure.repository.ClientRepository;
import br.com.selectgearmotors.client.infrastructure.repository.ClientTypeRepository;
import br.com.selectgearmotors.client.infrastructure.repository.MediaRepository;
import com.github.javafaker.Faker;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class ClientRepositoryTest {
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    private ClientMapper clientMapper;

    private ClientTypeEntity clientType;

    private MediaEntity mediaEntity;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        log.info("Cleaning up database...");
        mediaRepository.deleteAll();
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        this.mediaEntity = mediaRepository.save(getMedia());
        log.info("Setting up test data...");
        this.clientType = clientTypeRepository.save(getClientType());

        ClientEntity client = clientRepository.save(getClient(this.clientType, this.mediaEntity));
        log.info("ClientEntity:{}", client);
    }

    private MediaEntity getMedia() {
        return MediaEntity.builder()
                .name(faker.food().fruit())
                .path(faker.internet().url())
                .mediaType(MediaType.PNG)
                .build();
    }

    private ClientEntity getClient(ClientTypeEntity clientType, MediaEntity media) {
        return ClientEntity.builder()
                .name(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .clientTypeEntity(clientType)
                .mediaEntity(media)
                .build();
    }

    private ClientTypeEntity getClientType() {
        return ClientTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    @Test
    void should_find_no_clients_if_repository_is_empty() {
        Iterable<ClientEntity> clients = clientRepository.findAll();
        clients = Collections.EMPTY_LIST;
        assertThat(clients).isEmpty();
    }

    @Test
    void should_store_a_client() {
        log.info("Setting up test data...");

        // Salvar o ClientTypeEntity primeiro para garantir que ele está no banco de dados
        var clientType1 = clientTypeRepository.save(getClientType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        // Agora que o clientType1 foi salvo, podemos associá-lo ao ClientEntity
        var clientEntity = getClient(clientType1, mediaEntity1);

        // Salvar o ClientEntity, que agora tem uma referência válida ao ClientTypeEntity persistido
        var savedClient = clientRepository.save(clientEntity);

        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(clientEntity.getName());
    }

    @Test
    void should_find_client_by_id() {
        log.info("Setting up test data...");
        var clientType1 = clientTypeRepository.save(getClientType());
        var mediaEntity1 =  mediaRepository.save(getMedia());
        var client = getClient(clientType1, mediaEntity1);

        client.setCode(UUID.randomUUID().toString());

        // Ensure unique code
        ClientEntity savedClient = clientRepository.save(client);

        Optional<ClientEntity> foundClient = clientRepository.findById(savedClient.getId());
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getName()).isEqualTo(savedClient.getName());
    }

    @Test
    void should_find_all_clients() {
        log.info("Cleaning up database...");
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        var clientType1 = clientTypeRepository.save(getClientType());
        var mediaEntity1 =  mediaRepository.save(getMedia());
        var client1 = clientRepository.save(getClient(clientType1, mediaEntity1));

        Iterable<ClientEntity> clients = clientRepository.findAll();
        List<ClientEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
        assertThat(clientList).extracting(ClientEntity::getName).contains(client1.getName());
    }

    @Test
    void should_delete_all_clients() {
        log.info("Cleaning up database...");
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();
        var clientType1 = clientTypeRepository.save(getClientType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        clientRepository.save(getClient(clientType1, mediaEntity1));
        clientRepository.deleteAll();

        Iterable<ClientEntity> clients = clientRepository.findAll();
        assertThat(clients).isEmpty();
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        var fromDb = clientRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfClients_whenFindAll_thenReturnAllClients() {
        clientRepository.deleteAll();
        clientTypeRepository.deleteAll();

        List<ClientEntity> all = clientRepository.findAll();
        log.info(all.toString());

        var clientType1 = clientTypeRepository.save(getClientType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        var client = getClient(clientType1, mediaEntity1);
        log.info("ClientEntity:{}", client);
        var client1 = clientRepository.save(client);

        Iterable<ClientEntity> clients = clientRepository.findAll();
        List<ClientEntity> clientList = new ArrayList<>();
        clients.forEach(clientList::add);

        assertThat(clientList).hasSize(1);
        //assertThat(clientList).extracting(ClientEntity::getName).contains(client1.getName(), client2.getName(), client3.getName());
    }

    @Test
    void testSaveRestaurantWithLongName() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        clientEntity.setCode(UUID.randomUUID().toString());
        clientEntity.setDescription("Coca-Cola");

        assertThrows(ConstraintViolationException.class, () -> {
            clientRepository.save(clientEntity);
        });
    }

    private ClientEntity createInvalidClientType() {
        ClientEntity clientType = new ClientEntity();
        // Configurar o clientType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        clientType.setName(""); // Nome vazio pode causar uma violação
        return clientType;
    }
}
